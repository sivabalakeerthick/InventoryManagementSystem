package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.requestDTO.SupplierRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.SupplierResponseDTO;
import com.example.InventoryManagementSystem.entity.Supplier;
import com.example.InventoryManagementSystem.exception.SupplierNotFoundException;
import com.example.InventoryManagementSystem.repository.SupplierRepository;
import com.example.InventoryManagementSystem.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl; // Import PageImpl for manual Page creation
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections; // Import Collections
import java.util.Optional; // Import Optional

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SupplierResponseDTO addSupplier(SupplierRequestDTO supplierRequestDTO) {
        Supplier supplier = modelMapper.map(supplierRequestDTO, Supplier.class);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return modelMapper.map(savedSupplier, SupplierResponseDTO.class);
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier Not Found"));
        modelMapper.map(supplierRequestDTO, supplier);
        Supplier updatedSupplier = supplierRepository.save(supplier);
        return modelMapper.map(updatedSupplier, SupplierResponseDTO.class);
    }

    // Modified: Added Long searchId parameter and conditional logic
    @Override
    public Page<SupplierResponseDTO> getAllSuppliers(int page, int size, String sortBy, String sortOrder, String searchQuery , Pageable pageable) {
        Page<Supplier> supplierPage = null;
        if(searchQuery == null) {
         supplierPage = supplierRepository.findAll(pageable);
        }
        else{
            supplierPage = supplierRepository.findByNameStartingWithIgnoreCase(searchQuery , pageable);
        }
        return supplierPage.map(supplier -> modelMapper.map(supplier, SupplierResponseDTO.class));
    }

    @Override
    public SupplierResponseDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier Not Found"));
        return modelMapper.map(supplier, SupplierResponseDTO.class);
    }
    @Override
    public SupplierResponseDTO getSupplierByName(String name) {
        Supplier supplier = supplierRepository.findByName(name)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with name '" + name + "' not found"));
        return modelMapper.map(supplier, SupplierResponseDTO.class);
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierNotFoundException("Supplier Not Found");
        }
        supplierRepository.deleteById(id);
    }
}