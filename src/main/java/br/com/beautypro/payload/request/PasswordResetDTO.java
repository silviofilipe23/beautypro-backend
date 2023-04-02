package br.com.beautypro.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PasswordResetDTO {

    @NotBlank
    @Email
    private String email;

    public PasswordResetDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
