package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderItemRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.StockUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderItemResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseUpdateDTO;
import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.entity.PurchaseOrder;
import com.example.InventoryManagementSystem.entity.PurchaseOrderItem;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.enums.OrderType;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.exception.PurchaseOrderNotFoundException;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.exception.SupplierNotFoundException;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import com.example.InventoryManagementSystem.repository.PurchaseOrderRepository;
import com.example.InventoryManagementSystem.repository.SupplierRepository;
import com.example.InventoryManagementSystem.service.PurchaseOrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockServiceImpl stockService;

    @Override
    public Page<PurchaseOrderResponseDTO> getAllPurchaseOrders(int page, int size, String sortBy, String sortOrder,OrderStatus orderStatus, String searchQuery) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<PurchaseOrder> purchaseOrdersPage;

        if(orderStatus == null && searchQuery == null){
            purchaseOrdersPage = purchaseOrderRepository.findAll(pageable);
        }
        else if(orderStatus != null && searchQuery != null){
            purchaseOrdersPage = purchaseOrderRepository.findByOrderStatusAndSupplier_NameStartingWithIgnoreCase(orderStatus , searchQuery , pageable);
        }
        else if(orderStatus != null){
            purchaseOrdersPage = purchaseOrderRepository.findByOrderStatus(orderStatus , pageable);
        }
        else{
            purchaseOrdersPage = purchaseOrderRepository.findBySupplier_NameStartingWithIgnoreCase(searchQuery , pageable);
        }

        return purchaseOrdersPage.map(order -> {
            PurchaseOrderResponseDTO responseDTO = modelMapper.map(order, PurchaseOrderResponseDTO.class);
            // Ensure supplierName is mapped if not automatically handled by ModelMapper
            if (order.getSupplier() != null) {
                responseDTO.setSupplierName(order.getSupplier().getName());
            }
            responseDTO.setPurchaseOrderItemList(order.getPurchaseOrderItemList().stream()
                    .map(item -> modelMapper.map(item, PurchaseOrderItemResponseDTO.class))
                    .collect(Collectors.toList()));
            return responseDTO;
        });
    }

    @Override
    public PurchaseOrderResponseUpdateDTO updatePurchaseOrder(Long id, PurchaseOrderUpdateRequestDTO purchaseOrderUpdateRequestDTO) throws OutOfStockException, ProductNotFoundException {
        Optional<PurchaseOrder> result = purchaseOrderRepository.findById(id);
        if (result.isEmpty()) {
            throw new PurchaseOrderNotFoundException("Purchase Order Not Found with ID: " + id);
        }
        PurchaseOrder existingOrder = result.get();
        OrderStatus currentStatus = existingOrder.getOrderStatus();
        OrderStatus newStatus = purchaseOrderUpdateRequestDTO.getOrderStatus();
        if (isStatusProgressionValid(currentStatus, newStatus)) {
            existingOrder.setOrderStatus(newStatus);
            if(newStatus == OrderStatus.DELIVERED){
                updateQuantityForAllPurchasedProducts(existingOrder);
            }
            PurchaseOrder updatedOrder = purchaseOrderRepository.save(existingOrder);
        } else {
            throw new IllegalArgumentException("Invalid status progression. Current: " + currentStatus + ", Requested: " + newStatus);
        }
        return modelMapper.map(purchaseOrderRepository.save(existingOrder), PurchaseOrderResponseUpdateDTO.class);
    }

    public void updateQuantityForAllPurchasedProducts(PurchaseOrder purchaseOrder) throws OutOfStockException, ProductNotFoundException {
        for(PurchaseOrderItem purchaseOrderItem : purchaseOrder.getPurchaseOrderItemList()){
            StockUpdateRequestDTO stockUpdateRequestDTO = new StockUpdateRequestDTO();
            stockUpdateRequestDTO.setQuantity(purchaseOrderItem.getQuantity().intValue());
            stockUpdateRequestDTO.setOrderType(OrderType.PURCHASE);
            stockService.updateStockLevel(purchaseOrderItem.getProduct().getProductId(), stockUpdateRequestDTO);
        }
    }

    @Transactional
    @Override
    public PurchaseOrderResponseDTO addPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws ProductNotFoundException, OutOfStockException {
        // Map request DTO to entity
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplier(supplierRepository.findById(purchaseOrderRequestDTO.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("No such supplier exists")));
        purchaseOrder.setOrderStatus(OrderStatus.PENDING);
        // Note: PaymentType is not handled in the DTO to Entity mapping here.
        // Make sure it's set if you want it to be persisted.
        purchaseOrder.setPaymentType(purchaseOrderRequestDTO.getPaymentType()); // <-- Add this line
        List<PurchaseOrderItem> purchaseItems = new ArrayList<>();
        purchaseOrderRequestDTO.getPurchaseOrderItemList().forEach(System.out::println);
        double totalAmount = 0.0;

        for (PurchaseOrderItemRequestDTO dto : purchaseOrderRequestDTO.getPurchaseOrderItemList()) {

            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setQuantity(dto.getQuantity());
            item.setProduct(productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found")));
            item.setPurchaseOrder(purchaseOrder);
            item.setAmount(item.getQuantity() * item.getProduct().getUnitPrice());
            totalAmount += item.getAmount();
            purchaseItems.add(item);
        }

        purchaseOrder.setPurchaseOrderItemList(purchaseItems);
        purchaseOrder.setTotalAmount(totalAmount);


        PurchaseOrder result = purchaseOrderRepository.save(purchaseOrder);

        // Map saved entity to response DTO
        PurchaseOrderResponseDTO purchaseOrderResponseDTO = modelMapper.map(result, PurchaseOrderResponseDTO.class);
        purchaseOrderResponseDTO.setPurchaseOrderItemList(purchaseItems.stream()
                .map(item -> modelMapper.map(item, PurchaseOrderItemResponseDTO.class))
                .collect(Collectors.toList()));
        // Ensure supplierName is set in response DTO after mapping
        if (result.getSupplier() != null) {
            purchaseOrderResponseDTO.setSupplierName(result.getSupplier().getName());
        }

        return purchaseOrderResponseDTO;
    }


    @Override
    public PurchaseOrderResponseDTO getPurchaseOrderById(Long id) throws PurchaseOrderNotFoundException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseOrderNotFoundException("Purchase Order not found with ID: " + id));

        PurchaseOrderResponseDTO responseDTO = modelMapper.map(purchaseOrder, PurchaseOrderResponseDTO.class);
        // Map supplierName and purchase order items manually if ModelMapper doesn't handle nested properties well
        if (purchaseOrder.getSupplier() != null) {
            responseDTO.setSupplierName(purchaseOrder.getSupplier().getName());
        }
        if (purchaseOrder.getPurchaseOrderItemList() != null) {
            responseDTO.setPurchaseOrderItemList(purchaseOrder.getPurchaseOrderItemList().stream()
                    .map(item -> modelMapper.map(item, PurchaseOrderItemResponseDTO.class))
                    .collect(Collectors.toList()));
        }
        return responseDTO;
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        // You might want to add a check if the purchase order exists before deleting
        if (!purchaseOrderRepository.existsById(id)) {
            throw new PurchaseOrderNotFoundException("Purchase Order not found with ID: " + id);
        }
        purchaseOrderRepository.deleteById(id);
    }
}