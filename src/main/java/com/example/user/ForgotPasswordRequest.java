package com.example.user;

import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {
    @NotBlank(message = "Email is required")
    private String email;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

