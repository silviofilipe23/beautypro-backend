package br.com.beautypro.services.repository;

import br.com.beautypro.models.Product;
import br.com.beautypro.models.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProduct(Product product);
}
