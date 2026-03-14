package com.ketchup.todos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PasswordUpdateRequest {

    @NotEmpty(message = "The old password is mandatory")
    @Size(min = 8, max = 30, message = "Password must be 8 characters long")
    private String oldPassword;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, max = 30, message = "Password must be 8 characters long")
    private String newPassword;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, max = 30, message = "Password must be 8 characters long")
    private String confirmPassword;

    public PasswordUpdateRequest(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
