package com.example.InventoryManagementSystem.dto.responseDTO;

import com.example.InventoryManagementSystem.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseOrderResponseDTO {
    private Long purchaseOrderId;
    private String supplierName;
    private List<PurchaseOrderItemResponseDTO> purchaseOrderItemList;
    private Double totalAmount;
    private OrderStatus orderStatus;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    private String paymentType;
    private LocalDate purchaseDate;


    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public void setPurchaseOrderItemList(List<PurchaseOrderItemResponseDTO> purchaseOrderItemList) {
        this.purchaseOrderItemList = purchaseOrderItemList;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<PurchaseOrderItemResponseDTO> getPurchaseOrderItemList() {
        return purchaseOrderItemList;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}


