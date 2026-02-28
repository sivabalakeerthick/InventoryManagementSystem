//package com.example.InventoryManagementSystem.controller;
//
//import com.example.InventoryManagementSystem.dto.requestDTO.ProductRequestDto;
//import com.example.InventoryManagementSystem.dto.responseDTO.ProductResponseDto;
//import com.example.InventoryManagementSystem.service.impl.ProductServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//public class ProductControllerTest {
//
//    @Mock
//    private ProductServiceImpl productService;
//
//    @InjectMocks
//    private ProductController productController;
//
//    private MockMvc mockMvc;
//
//    @Test
//    public void testGetAllProducts() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
//
//        // Mock response data
//        ProductResponseDto product = new ProductResponseDto();
//        product.setProductName("Test Product");
//        product.setUnitPrice(100.0);
//
//        when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));
//
//        // Perform GET request
//        mockMvc.perform(get("/product")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].productName").value("Test Product"))
//                .andExpect(jsonPath("$[0].unitPrice").value(100.0));
//    }
//
//
//    @Test
//    public void testGetProductById() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
//
//        // Mock product response
//        ProductResponseDto product = new ProductResponseDto();
//        product.setProductName("Sample Product");
//        product.setUnitPrice(300.0);
//
//        when(productService.getProductById(1L)).thenReturn(product);
//
//        // Perform GET request
//        mockMvc.perform(get("/product/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productName").value("Sample Product"))
//                .andExpect(jsonPath("$.unitPrice").value(300.0));
//    }
//
//
//}
