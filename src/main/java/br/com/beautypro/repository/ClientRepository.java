package br.com.beautypro.repository;

import br.com.beautypro.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Client save(Client clients);

}
