package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.responseDTO.OrderResponseDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.repository.UserRepository;
import com.example.InventoryManagementSystem.service.impl.OrderServiceImpl;
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
public class OrderControllerTest {

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

//    @Test
//    public void testGetAllOrders() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
//
//        // Mock response data
//        OrderResponseDTO order = new OrderResponseDTO();
//        order.setOrderId(1L);
//        order.setOrderStatus(OrderStatus.PENDING);
//
//        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));
//
//        // Perform GET request
//        mockMvc.perform(get("/order")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].orderId").value(1L))
//                .andExpect(jsonPath("$[0].orderStatus").value(OrderStatus.PENDING.name()));
//
//    }



    @Test
    public void testGetOrderById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        // Mock response data
        OrderResponseDTO order = new OrderResponseDTO();
        order.setOrderId(1L);
        order.setOrderStatus(OrderStatus.DELIVERED);

        when(orderService.getOrderById(1L)).thenReturn(order);

        // Perform GET request
        mockMvc.perform(get("/order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.DELIVERED.name())); // Ensure proper string comparison
    }
}
