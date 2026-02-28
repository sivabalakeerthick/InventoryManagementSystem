package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.requestDTO.UserDTO;
import com.example.InventoryManagementSystem.entity.User;
import com.example.InventoryManagementSystem.enums.UserRole;
import com.example.InventoryManagementSystem.exception.UserAlreadyExistException;
import com.example.InventoryManagementSystem.exception.UserNotFoundException;
import com.example.InventoryManagementSystem.util.UserInfoDetails;
import com.example.InventoryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(usernameOrEmail); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetail(user entity)
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
    }

    public String addUser(UserDTO userInfo) throws UserAlreadyExistException {

        if (repository.findByEmail(userInfo.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User already exists!");
        }
        // Encode password before saving the user
        User user = new User();
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setPassword(encoder.encode(userInfo.getPassword()));
        user.setUserRole(userInfo.getUserRole());
        // Assign manager if provided
        repository.save(user);
        return "User Added Successfully";
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map(User::getDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userDetail = repository.findById(id);// Assuming 'email' is used as username
        if (userDetail.isPresent()) {
            return userDetail.get().getDTO();
        } else {
            throw new UserNotFoundException("User not found with id" + id);
        }
    }


    public List<UserDTO> getAllEmployees() {
        List<User> employees = repository.findByUserRole(UserRole.STAFF);
        return employees.stream().map(User::getDTO).collect(Collectors.toList());
    }


    public String updateUser(Long userId, UserDTO userDTO) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(userDTO.getPassword()));  // Update password if provided
        }

        if (userDTO.getUserRole() != null) {
            user.setUserRole(userDTO.getUserRole());  // Update role if provided
        }


        repository.save(user);
        return "User updated successfully";
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
