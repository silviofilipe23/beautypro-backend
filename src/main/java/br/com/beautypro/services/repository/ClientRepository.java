package br.com.beautypro.services.repository;

import br.com.beautypro.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client save(Client clients);

    Optional<Client> findById(Long id);

    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
    Boolean existsByRg(String rg);

    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
