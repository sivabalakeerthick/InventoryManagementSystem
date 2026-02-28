package com.example.InventoryManagementSystem.dto.requestDTO;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequestDTO {
    private Long supplierId;
    private List<PurchaseOrderItemRequestDTO> purchaseOrderItemList = new ArrayList<>();
    private String paymentType;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<PurchaseOrderItemRequestDTO> getPurchaseOrderItemList() {
        return purchaseOrderItemList;
    }

    public void setPurchaseOrderItemList(List<PurchaseOrderItemRequestDTO> purchaseOrderItemList) {
        this.purchaseOrderItemList = purchaseOrderItemList;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}