package com.example.InventoryManagementSystem.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long supplierId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private long contact;

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public Long getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public Long getSupplierId() {
        return supplierId;
    }


}
