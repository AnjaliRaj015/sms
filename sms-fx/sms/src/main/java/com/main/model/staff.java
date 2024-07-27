package com.main.model;

public class staff extends user {
    // Constructor to initialize all fields
    public staff(String username, String password, String fullName, String email, String phone, String address) {
        this.setUsername(username);
        this.setPassword(password);
        this.setName(fullName);
        this.setEmail(email);
        this.setPhone(phone);
        this.setAddress(address);
        this.setRole("staff"); // Explicitly set role to 'staff'
    }
}
