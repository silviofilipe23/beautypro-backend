package br.com.beautypro.services.repository;

import br.com.beautypro.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

//    List<Service> findByDateHourBetween(LocalDateTime start, LocalDateTime end);
}
