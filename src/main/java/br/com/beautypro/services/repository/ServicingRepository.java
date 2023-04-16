package br.com.beautypro.services.repository;

import br.com.beautypro.models.Servicing;
import br.com.beautypro.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicingRepository extends JpaRepository<Servicing, Long> {
    boolean existsById(Long id);

    Page<Servicing> findByActive(boolean active, Pageable pageable);

    Page<Servicing> findByDescriptionContainingIgnoreCase(String name, Pageable pageable);

    Page<Servicing> findByDescriptionContainingIgnoreCaseAndActive(String name, boolean active, Pageable pageable);

}
