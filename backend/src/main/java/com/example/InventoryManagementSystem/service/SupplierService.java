package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.requestDTO.SupplierRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.SupplierResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SupplierService {
    SupplierResponseDTO addSupplier(SupplierRequestDTO supplierRequestDTO);

    SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO);

    Page<SupplierResponseDTO> getAllSuppliers(int page, int size, String sortBy, String sortOrder, String searchQuery, Pageable pageable);

    SupplierResponseDTO getSupplierById(Long id);

    SupplierResponseDTO getSupplierByName(String name);

    void deleteSupplier(Long id);
}