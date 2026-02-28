package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.responseDTO.StockResponseDTO;
import com.example.InventoryManagementSystem.service.impl.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
public class StockControllerTest {

    @Mock
    private StockServiceImpl stockService;

    @InjectMocks
    private StockController stockController;

    private MockMvc mockMvc;

//    @Test
//    public void testGetAllStocks() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
//
//        // Mock response data
//        StockResponseDTO stock = new StockResponseDTO();
//        stock.setQuantity(50);
//
//        when(stockService.getAllStock()).thenReturn(Collections.singletonList(stock));
//
//        // Perform GET request
//        mockMvc.perform(get("/stock")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].quantity").value(50));
//    }


    @Test
    public void testGetStockById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();

        // Mock response data
        StockResponseDTO stock = new StockResponseDTO();

        stock.setQuantity(100);

        when(stockService.getStockById(1L)).thenReturn(stock);

        // Perform GET request
        mockMvc.perform(get("/stock/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(100));
    }

}
