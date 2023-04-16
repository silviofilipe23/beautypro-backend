package br.com.beautypro.payload.request;

import br.com.beautypro.models.Address;
import br.com.beautypro.models.Person;

import java.util.Set;

import javax.persistence.Lob;
import javax.validation.constraints.*;

public class SignupRequest extends Person {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(min = 3, max = 20)
  private String name;

  @NotBlank
  @Size(min = 3, max = 20)
  private String phoneNumber;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Lob
  private String observations;


  private boolean active;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  @NotNull
  private Address address;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
