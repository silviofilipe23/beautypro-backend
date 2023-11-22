package br.com.beautypro.controllers;

import br.com.beautypro.models.Product;
import br.com.beautypro.models.Supplier;
import br.com.beautypro.models.UnitOfMeasure;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ProdutctRepository;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProdutctRepository produtctRepository;

    @Autowired
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<PageableResponse> getAllProducts(@Valid @RequestParam int page, @RequestParam int size, @RequestParam(required=false) String name) {

        if (name == null) {
            PageableResponse clients = productService.getAllProducts(page, size);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } else {
            PageableResponse clients = productService.listProductsFilter(page, size, name);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@Valid @PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product productRequest) {

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureService.getUnitOfMeasureById(productRequest.getUnitOfMeasure().getId());

        if (unitOfMeasure.isPresent()) {
            productRequest.setUnitOfMeasure(unitOfMeasure.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Unidade de medida não encontrada!"));
        }

        Optional<Supplier> supplier = supplierService.getSupplierById(productRequest.getSupplier().getId());

        if (supplier.isPresent()) {
            productRequest.setSupplier(supplier.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fornecedor não encontrado!"));
        }

        Product savedProduct = productService.saveProduct(productRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable("id") Long id, @RequestBody Product productRequest) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {

            Product product = existingProduct.get();

            product.setQuantity(productRequest.getQuantity());
            product.setSupplier(productRequest.getSupplier());
            product.setActive(productRequest.isActive());
            product.setUnitOfMeasure(productRequest.getUnitOfMeasure());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setCode(productRequest.getCode());
            product.setNotes(productRequest.getNotes());
            product.setBrand(productRequest.getBrand());

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
