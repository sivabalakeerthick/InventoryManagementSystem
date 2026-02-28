package com.example.InventoryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.example.InventoryManagementSystem")
public class InventoryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementSystemApplication.class, args);
    }

}


