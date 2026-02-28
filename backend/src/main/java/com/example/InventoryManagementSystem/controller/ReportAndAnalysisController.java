package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.responseDTO.OrderStatusCountDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockInfoDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.TopSupplierResponseDTO;
import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductQuantityResponseDTO;
import com.example.InventoryManagementSystem.entity.PurchaseOrder;
import com.example.InventoryManagementSystem.dto.requestDTO.ReportAndAnalysisDTO;
import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import com.example.InventoryManagementSystem.service.impl.ReportAndAnalysisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class ReportAndAnalysisController {
    @Autowired
    private ReportAndAnalysisServiceImpl reportAndAnalysisServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/toporders")
    public ResponseEntity<List<Order>> findTopOrders(@RequestParam String startDate , @RequestParam String endDate) {

            LocalDate parsedStartDate = LocalDate.parse(startDate);
            LocalDate parsedEndDate = LocalDate.parse(endDate.trim());

        System.out.println("in controller");
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTopExpensiveOrders(parsedStartDate , parsedEndDate));

    }

   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("/totalamount")
    public ResponseEntity<Double> findTotalAmount(@RequestParam String startDate , @RequestParam String endDate) {
       LocalDate parsedStartDate = LocalDate.parse(startDate);
       LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTotalAmount(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalorders")
    public ResponseEntity<Long> findTotalOrders(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTotalOrders(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/toppurchaseorders")
    public ResponseEntity<List<PurchaseOrder>> findTopPurchaseOrders(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTopExpensivePurchaseOrders(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalpurchaseamount")
    public ResponseEntity<Double> findTotalPurchaseAmount(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTotalPurchaseAmount(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalpurchaseorders")
    public ResponseEntity<Long> findTotalPurchaseOrders(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTotalPurchaseOrders(parsedStartDate,parsedEndDate));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orderproductquantity")
    public ResponseEntity<List<ProductQuantityResponseDTO>> findOrderProductQuantity(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findOrderProductQuantity(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/purchaseproductquantity")
    public ResponseEntity<List<ProductQuantityResponseDTO>> findPurchaseProductQuantity(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findPurchaseProductQuantity(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalpurchasequantity")
    public ResponseEntity<Long> findTotalPurchaseQuantity(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTotalPurchaseQuantity(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalorderquantity")
    public ResponseEntity<Long> findTotalOrderQuantity(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTotalOrderQuantity(parsedStartDate,parsedEndDate));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/topsuppliers")
    public ResponseEntity<List<TopSupplierResponseDTO>> findTopSuppliers(@RequestParam String startDate , @RequestParam String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate.trim());
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.findTopSuppliers(parsedStartDate,parsedEndDate));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/countorderbystatus")
    public ResponseEntity<List<OrderStatusCountDTO>> countOrdersByStatus() {
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.countOrdersByStatus());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/countpurchaseorderbystatus")
    public ResponseEntity<List<OrderStatusCountDTO>> countPurchaseOrdersByStatus() {
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.countPurchaseOrdersByStatus());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inventoryvalue")
    public ResponseEntity<Double> getTotalStockValueBetweenDates() {
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.getTotalStockValue());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalsuppliers")
    public ResponseEntity<Long> getTotalSuppliers() {
        return ResponseEntity.ok(reportAndAnalysisServiceImpl.getTotalsuppliers());
    }



}
