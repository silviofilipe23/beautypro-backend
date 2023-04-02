package br.com.beautypro.services;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.Product;
import br.com.beautypro.models.Servicing;
import br.com.beautypro.models.UnitOfMeasure;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.repository.ProdutctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProdutctRepository productRepository;

//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }

    public boolean getProductByName(String name) {
        return productRepository.existsByName(name);
    }

    public PageableResponse getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productResponse = productRepository.findAll(pageable);
        List<Product> productList = productResponse.stream()
                .map(product -> new Product(product.getId(), product.getName(), product.getPrice(), product.isActive(),product.getQuantity(),product.getUnitOfMeasure(), product.getSupplier()))
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        System.out.println(productList.size());

        response.setData(productList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    public PageableResponse listProductsFilter(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productResponse = productRepository.findByNameContainingIgnoreCase(name, pageable);
        List<Product> productList = productResponse.stream()
                .map(product -> new Product(product.getId(), product.getName(), product.getPrice(), product.isActive(),product.getQuantity(),product.getUnitOfMeasure(), product.getSupplier()))
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        System.out.println(productList.size());

        response.setData(productList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
