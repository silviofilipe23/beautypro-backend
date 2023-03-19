package br.com.beautypro.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SupplierRequest {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 14)
    private String cnpj;

    @Size(max = 64)
    @Email
    private String email;

    @Size(max = 11)
    private String phoneNumber;


    @Size(max = 512)
    private String street;

    @Size(max = 10)
    private String number;

    @Size(max = 512)
    private String complement;

    @Size(max = 32)
    private String district;

    @Size(max = 32)
    private String city;

    @Size(max = 32)
    private String state;

    @Size(max = 8)
    private String cep;

    public SupplierRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
