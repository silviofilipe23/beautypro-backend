package br.com.beautypro.repository;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutctRepository extends JpaRepository<Product, Long> {

    Boolean existsByName(String name);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
