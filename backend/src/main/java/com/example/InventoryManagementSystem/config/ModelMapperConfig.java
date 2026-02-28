package com.example.InventoryManagementSystem.config;


import com.example.InventoryManagementSystem.dto.responseDTO.StockResponseDTO;
import com.example.InventoryManagementSystem.entity.Stock;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderItemResponseDTO;
import com.example.InventoryManagementSystem.entity.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Stock.class, StockResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getProduct().getProductName(), StockResponseDTO::setName);
            mapper.map(src -> src.getProduct().getReOrderLevel(), StockResponseDTO::setReOrderLevel);
        });
        modelMapper.typeMap(OrderItem.class, OrderItemResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getProduct().getUnitPrice(), OrderItemResponseDTO::setUnitPrice);
            mapper.map(src -> src.getProduct().getCategory().getCategoryName(), OrderItemResponseDTO::setCategoryName);
        });
        return modelMapper;
    }
}
