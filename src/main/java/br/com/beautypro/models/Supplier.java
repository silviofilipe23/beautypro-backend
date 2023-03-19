package br.com.beautypro.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "supplier", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cnpj"),
        @UniqueConstraint(columnNames = "email")
})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 14)
    private String cnpj;

    @Size(max = 64)
    private String email;


    @Size(max = 11)
    private String phoneNumber;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public Supplier() {
    }

    public Supplier(String name, String cnpj, String email, String phoneNumber, boolean active, Address address) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
