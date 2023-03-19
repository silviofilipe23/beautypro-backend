package br.com.beautypro.repository;

import br.com.beautypro.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client save(Client clients);

    Optional<Client> findById(Long id);

    Boolean existsByEmail(String email);

}
