package br.com.beautypro.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewPasswordDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String token;

    public NewPasswordDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "NewPasswordDTO{" +
                "email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
