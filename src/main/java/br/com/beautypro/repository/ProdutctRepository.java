package br.com.beautypro.repository;

import br.com.beautypro.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutctRepository extends JpaRepository<Product, Long> {
}
