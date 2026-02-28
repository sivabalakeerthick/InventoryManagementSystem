package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.requestDTO.SupplierRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.SupplierResponseDTO;
import com.example.InventoryManagementSystem.service.impl.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    // Modified: Added optional @RequestParam for searchId
    public ResponseEntity<Page<SupplierResponseDTO>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "supplierId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String searchQuery) // New: Optional searchId
    {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SupplierResponseDTO> responsePage = supplierServiceImpl.getAllSuppliers(page, size, sortBy, sortOrder, searchQuery , pageable);
        return ResponseEntity.ok(responsePage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> addSupplier(@RequestBody SupplierRequestDTO supplierrequestdto) {
        return ResponseEntity.ok(supplierServiceImpl.addSupplier(supplierrequestdto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierServiceImpl.getSupplierById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name/{name}")
    public ResponseEntity<SupplierResponseDTO> getSupplierByName(@PathVariable String name) {
        return ResponseEntity.ok(supplierServiceImpl.getSupplierByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequestDTO supplierRequestDTO) {
        return ResponseEntity.ok(supplierServiceImpl.updateSupplier(id, supplierRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        supplierServiceImpl.deleteSupplier(id);
        return ResponseEntity.ok("Supplier Deleted Successfully");
    }
}