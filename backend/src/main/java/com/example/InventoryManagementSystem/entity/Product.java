package com.example.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer reOrderLevel;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = false)
//    private List<OrderItem> orderItems;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = false)
//    private List<PurchaseOrderItem> purchaseOrderItems;

    @Column(nullable = false)
    private boolean active;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }


    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductName() {
        return productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", description='" + description + '\'' +
                ", reOrderLevel=" + reOrderLevel +
                '}';
    }

    @PrePersist
    public void setActiveTrue(){
        this.active = true;
    }

    public void setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }


}
