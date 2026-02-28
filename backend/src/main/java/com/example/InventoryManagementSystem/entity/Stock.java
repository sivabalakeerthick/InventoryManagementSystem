package com.example.InventoryManagementSystem.entity;

import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "stock")
public class Stock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @Version
    private Long version = 0L;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate lastUpdated;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StockLevelStatus status;

    public Long getStockId() {
        return stockId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @PrePersist
    public void onCreate() {
        this.lastUpdated = LocalDate.now();
    }

    public void updateStockStatus() {
        if(!product.isActive()){
            this.status = StockLevelStatus.NOT_AVAILABLE;
            return;
        }
        if (quantity == 0) {
            this.status =  StockLevelStatus.OUT_OF_STOCK;
        } else if (quantity <= getProduct().getReOrderLevel()) {
            this.status = StockLevelStatus.LOW_STOCK;
        } else {
            this.status = StockLevelStatus.IN_STOCK;
        }
    }

}
