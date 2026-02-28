package com.example.InventoryManagementSystem.dto.requestDTO;

public class SupplierRequestDTO {


    private String name;
    private Long contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}