package com.example.InventoryManagementSystem.dto.requestDTO;

import com.example.InventoryManagementSystem.entity.PurchaseOrderItem;
import com.example.InventoryManagementSystem.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
    private Supplier supplier;
    private List<PurchaseOrderItem> purchaseOrderItemList = new ArrayList<>();
    private String paymentType;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<PurchaseOrderItem> getPurchaseOrderItemList() {
        return purchaseOrderItemList;
    }

    public void setPurchaseOrderItemList(List<PurchaseOrderItem> purchaseOrderItemList) {
        this.purchaseOrderItemList = purchaseOrderItemList;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
