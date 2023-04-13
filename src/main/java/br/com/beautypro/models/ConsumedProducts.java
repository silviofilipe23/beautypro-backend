package br.com.beautypro.models;

import javax.persistence.*;
@Entity
public class ConsumedProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "servicing_id")
//    private ConsumedProducts servicingProducts;

    private int quantity;

    public ConsumedProducts() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    public ConsumedProducts getServicingProducts() {
//        return servicingProducts;
//    }
//
//    public void setServicingProducts(ConsumedProducts servicingProducts) {
//        this.servicingProducts = servicingProducts;
//    }
}
