package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.service.impl.PurchaseOrderServiceImpl;
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
public class PurchaseOrderControllerTest {

    @Mock
    private PurchaseOrderServiceImpl purchaseOrderService;

    @InjectMocks
    private PurchaseOrderController purchaseOrderController;

    private MockMvc mockMvc;

//    @Test
//    public void testGetAllPurchaseOrders() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderController).build();
//
//        // Mock response data
//        PurchaseOrderResponseDTO purchaseOrder = new PurchaseOrderResponseDTO();
//        purchaseOrder.setPurchaseOrderId(1L);
//        purchaseOrder.setOrderStatus(OrderStatus.PENDING);
//
//        when(purchaseOrderService.getAllPurchaseOrders()).thenReturn(Collections.singletonList(purchaseOrder));
//
//        // Perform GET request
//        mockMvc.perform(get("/purchaseOrder")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].purchaseOrderId").value(1L))
//                .andExpect(jsonPath("$[0].orderStatus").value(OrderStatus.PENDING.name()));
//    }

    @Test
    public void testGetPurchaseOrderById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderController).build();

        // Mock response data
        PurchaseOrderResponseDTO purchaseOrder = new PurchaseOrderResponseDTO();
        purchaseOrder.setPurchaseOrderId(1L);
        purchaseOrder.setOrderStatus(OrderStatus.DELIVERED);

        when(purchaseOrderService.getPurchaseOrderById(1L)).thenReturn(purchaseOrder);

        // Perform GET request
        mockMvc.perform(get("/purchaseOrder/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseOrderId").value(1L))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.DELIVERED.name()));
    }

}
