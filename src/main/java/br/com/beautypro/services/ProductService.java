package br.com.beautypro.services;

import br.com.beautypro.models.Product;
import br.com.beautypro.models.UnitOfMeasure;
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

    public List<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productResponse = productRepository.findAll(pageable);
        List<Product> productList = productResponse.stream()
                .collect(Collectors.toList());
        return productList;
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
