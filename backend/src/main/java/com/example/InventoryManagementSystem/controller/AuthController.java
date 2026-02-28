package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.requestDTO.AuthRequest;
import com.example.InventoryManagementSystem.dto.requestDTO.UserDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.AuthResponse;
import com.example.InventoryManagementSystem.entity.User;
import com.example.InventoryManagementSystem.exception.UserNotFoundException;
import com.example.InventoryManagementSystem.repository.UserRepository;
import com.example.InventoryManagementSystem.service.impl.UserInfoService;
import com.example.InventoryManagementSystem.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addNewUser")
    public String addNewUser(@Valid @RequestBody UserDTO userDTO) {
        return service.addUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            // If authentication is successful, retrieve user details
            User user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            // Generate JWT and return auth response
            AuthResponse authResponse = new AuthResponse(jwtService.generateToken(authRequest.getEmail(), user.getUserRole().name()), user.getUserRole().name());
            return ResponseEntity.ok(authResponse);
            // Should never reach this point unless authentication fails unexpectedly
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authentication failed"));

        } catch (AuthenticationException ex) {
            // Return JSON response for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

}


