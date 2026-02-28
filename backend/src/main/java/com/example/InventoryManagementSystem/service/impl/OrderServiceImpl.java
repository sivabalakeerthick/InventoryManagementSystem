package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.requestDTO.OrderItemDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.OrderRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.StockUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderItemResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderUpdateResponseDTO;
import com.example.InventoryManagementSystem.entity.*;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.enums.OrderType;
import com.example.InventoryManagementSystem.exception.OrderNotFoundException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import com.example.InventoryManagementSystem.repository.OrderRepository;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import com.example.InventoryManagementSystem.service.OrderService;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StockServiceImpl stockService;

    @Override
    public Page<OrderResponseDTO> getAllOrders(OrderStatus orderStatus , String searchQuery , Pageable pageable) {
        Page<Order> orders = null;

        if(orderStatus == null && searchQuery == null){
            orders = orderRepository.findAll(pageable);
        }
        else if(orderStatus != null && searchQuery != null) {
            orders = orderRepository.findByOrderStatusAndCustomer_CustomerNameStartingWithIgnoreCase(orderStatus , searchQuery , pageable);
        }
        else if(orderStatus != null) {
            orders = orderRepository.findByOrderStatus(orderStatus, pageable);
        }
        else{
            orders = orderRepository.findByCustomer_CustomerNameStartingWithIgnoreCase(searchQuery , pageable);
        }

        return orders
                .map(order -> {
                    OrderResponseDTO orderDTO = modelMapper.map(order, OrderResponseDTO.class);
                    List<OrderItemResponseDTO> orderItemDTOs = order.getOrderItems().stream()
                            .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
                            .collect(Collectors.toList());
                    orderDTO.setOrderItems(orderItemDTOs);
                    return orderDTO;
                });
    }


    @Transactional
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) throws ProductNotFoundException, OutOfStockException {
        Order order = modelMapper.map(orderRequestDTO, Order.class);

        // Ensure the customer exists or save a new one
        Optional<Customer> existingCustomer = customerRepository.findByCustomerEmail(order.getCustomer().getCustomerEmail());
        Customer customer = existingCustomer.orElseGet(() -> customerRepository.save(order.getCustomer()));
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.PENDING);

        // Initialize orderItems list
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        // Map OrderItemDTO to OrderItem
        for (OrderItemDTO dto : orderRequestDTO.getOrderItems()) {
            OrderItem item = new OrderItem();
            item.setQuantity(dto.getQuantity());
            item.setProduct(productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found")));
            item.setOrder(order);
            item.setPrice(item.getQuantity() * item.getProduct().getUnitPrice());
            totalAmount += item.getPrice();
            orderItems.add(item);

        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalAmount);


        // Save the order along with all items
        Order savedOrder = orderRepository.save(order);

        // Convert to DTO
        OrderResponseDTO orderResponseDTO = modelMapper.map(savedOrder, OrderResponseDTO.class);
        orderResponseDTO.setOrderItems(orderItems.stream()
                .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
                .collect(Collectors.toList()));

        return orderResponseDTO;
    }


    @Override
    public OrderResponseDTO getOrderById(Long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderUpdateResponseDTO updateOrder(Long id, OrderStatus status) throws OrderNotFoundException, OutOfStockException, ProductNotFoundException {

        Optional<Order> result = Optional.ofNullable(orderRepository.findById(id)).orElseThrow(() -> (
                new OrderNotFoundException("Order not found")));



        Order order = null;
        if (result.isPresent())
            order = result.get();

        OrderStatus currentStatus = order.getOrderStatus();
        OrderStatus newStatus = status;
        if (isStatusProgressionValid(currentStatus, newStatus)) {
            order.setOrderStatus(newStatus);
            if(newStatus == OrderStatus.SHIPPED){
                updateQuantityForAllShippedProducts(order);
            }
            else if(newStatus == OrderStatus.CANCELLED){
                reStockOrder(order);
            }
            Order updatedOrder = orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Invalid status progression. Current: " + currentStatus + ", Requested: " + newStatus);
        }
        return modelMapper.map(orderRepository.save(order), OrderUpdateResponseDTO.class);
    }

    public void updateQuantityForAllShippedProducts(Order order) throws OutOfStockException, ProductNotFoundException {
        for(OrderItem orderItem : order.getOrderItems()){
            StockUpdateRequestDTO stockUpdateRequestDTO = new StockUpdateRequestDTO();
            stockUpdateRequestDTO.setQuantity(orderItem.getQuantity().intValue());
            stockUpdateRequestDTO.setOrderType(OrderType.SALES);
            stockService.updateStockLevel(orderItem.getProduct().getProductId(), stockUpdateRequestDTO);
        }
    }

    public void reStockOrder(Order order) throws OutOfStockException, ProductNotFoundException {
        for(OrderItem orderItem : order.getOrderItems()){
            StockUpdateRequestDTO stockUpdateRequestDTO = new StockUpdateRequestDTO();
            stockUpdateRequestDTO.setQuantity(orderItem.getQuantity().intValue());
            stockUpdateRequestDTO.setOrderType(OrderType.PURCHASE);
            stockService.updateStockLevel(orderItem.getProduct().getProductId(), stockUpdateRequestDTO);
        }
    }


}