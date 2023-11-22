package br.com.beautypro.payload.request;

import br.com.beautypro.models.City;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequest {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 512)
    private String street;

    @Size(max = 10)
    private String number;

    @Size(max = 512)
    private String complement;

    @Size(max = 32)
    private String district;

    @Size(max = 32)
    private City city;

    @Size(max = 32)
    private String state;

    @Size(max = 8)
    private String cep;

    private boolean active;

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
