package br.com.beautypro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    private double price;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @NotNull
    private double quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measure_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UnitOfMeasure unitOfMeasure;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    public Product() {
    }

    public Product(Long id, String name, double price, boolean active, double quantity, UnitOfMeasure unitOfMeasure, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.supplier = supplier;
    }

    public Product(String name, double price, boolean active, double quantity, UnitOfMeasure unitOfMeasure, Supplier supplier) {
        this.name = name;
        this.price = price;
        this.active = active;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.supplier = supplier;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
