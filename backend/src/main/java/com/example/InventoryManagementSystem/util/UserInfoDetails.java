package com.example.InventoryManagementSystem.util;
//Notes:
//This class is used by Spring Security's authentication system.
//When a user tries to log in, Spring Security loads the user details using this class.
//It checks the password, roles, and account status.

import com.example.InventoryManagementSystem.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {

    private String email; // Changed from 'name' to 'email' for clarity
    private String password;
    private String authority;

    public UserInfoDetails(User userInfo) {
        this.email = userInfo.getEmail(); // Use email as username
        this.password = userInfo.getPassword();
        this.authority = userInfo.getUserRole().name();
        //user's role (UserRole) from the User entity and assigns it to authority->.name() converts an enum value (UserRole) into a string representation of its name.
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + authority));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}