package br.com.beautypro.services.repository;

import br.com.beautypro.models.Servicing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicingRepository extends JpaRepository<Servicing, Long> {
    boolean existsById(Long id);

}
