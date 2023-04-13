package br.com.beautypro.services;

import br.com.beautypro.models.MovementType;
import br.com.beautypro.models.Product;
import br.com.beautypro.models.StockMovement;
import br.com.beautypro.services.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private ProductService productService;

    public StockMovement addMovement(Long productId, Integer quantity, MovementType type) {
        Optional<Product> product = productService.getProductById(productId);
        StockMovement movement = new StockMovement();

        if (product.isPresent()) {
            movement.setProduct(product.get());
            movement.setQuantity(quantity);
            movement.setType(type);
            movement.setDate(new Date());

            if (type == MovementType.IN) {
                product.get().setQuantity(product.get().getQuantity() + quantity);
            } else {
                product.get().setQuantity(product.get().getQuantity() - quantity);
            }
            productService.saveProduct(product.get());
        }

        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> getMovementsByProduct(Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        return product.map(value -> stockMovementRepository.findByProduct(value)).orElse(null);
    }

    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

}
