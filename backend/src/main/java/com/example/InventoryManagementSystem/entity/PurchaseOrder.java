package com.example.InventoryManagementSystem.entity;

import com.example.InventoryManagementSystem.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "purchaseOrders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)// Explicit foreign key in PurchaseOrderItem
    private List<PurchaseOrderItem> purchaseOrderItemList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    private Double totalAmount;


    private String paymentType;

    private LocalDate purchasedDate;


    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }


    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @PrePersist
    public void onCreate() {
        this.purchasedDate = LocalDate.now();
    }


    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "purchaseOrderId=" + purchaseOrderId +
                ", supplier=" + supplier +
                ", purchaseOrderItemList=" + purchaseOrderItemList +
                ", totalAmount=" + totalAmount +
                ", paymentType='" + paymentType + '\'' +
                ", purchasedDate=" + purchasedDate +
                '}';
    }


}
