package com.ecom.user;

import javax.validation.constraints.NotBlank;

public class ResetPasswordRequest {
    @NotBlank(message = "Password is required")
    private String newPassword;

    // Getters and setters
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
