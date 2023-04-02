package br.com.beautypro.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordResetTokenDTO {

    @NotBlank
    @Email
    @Size(max = 60)
    private String email;

    @NotBlank
    private String token;

    public PasswordResetTokenDTO() {
    }

    public PasswordResetTokenDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
