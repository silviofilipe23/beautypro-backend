package br.com.beautypro.repository;

import br.com.beautypro.models.Role;
import br.com.beautypro.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByCnpj(String cnpj);

}
