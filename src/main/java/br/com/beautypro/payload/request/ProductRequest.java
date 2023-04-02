package br.com.beautypro.payload.request;

import br.com.beautypro.models.Supplier;
import br.com.beautypro.models.UnitOfMeasure;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductRequest {


    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    private double price;

    @NotNull
    private double quantity;

    @NotNull
    private Long idUnitOfMeasure;

    @NotNull
    private Long idSupplier;

    public ProductRequest() {
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Long getIdUnitOfMeasure() {
        return idUnitOfMeasure;
    }

    public void setIdUnitOfMeasure(Long idUnitOfMeasure) {
        this.idUnitOfMeasure = idUnitOfMeasure;
    }

    public Long getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Long idSupplier) {
        this.idSupplier = idSupplier;
    }
}
