package br.com.beautypro.controllers;

import br.com.beautypro.models.Product;
import br.com.beautypro.models.Supplier;
import br.com.beautypro.models.UnitOfMeasure;
import br.com.beautypro.payload.request.ProductRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.services.ProductService;
import br.com.beautypro.services.SupplierService;
import br.com.beautypro.services.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@Valid @RequestParam int page, @RequestParam int size) {
        List<Product> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@Valid @PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {

        Product product = new Product();

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureService.getUnitOfMeasureById(productRequest.getUnitOfMeasure().getId());

        if (unitOfMeasure.isPresent()) {
            product.setUnitOfMeasure(unitOfMeasure.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Unidade de medida não encontrada!"));
        }

        Optional<Supplier> supplier = supplierService.getSupplierById(productRequest.getSupplier().getId());

        if (supplier.isPresent()) {
            product.setSupplier(supplier.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fornecedor não encontrado!"));
        }

        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable("id") Long id, @RequestBody Product product) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = productService.saveProduct(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@Valid @PathVariable("id") Long id) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/unit-of-measure")
    public ResponseEntity<List<UnitOfMeasure>> getAllProducts() {
        List<UnitOfMeasure> unitOfMeasure = unitOfMeasureService.getAllUnitOfMeasures();
        return new ResponseEntity<>(unitOfMeasure, HttpStatus.OK);
    }

}
