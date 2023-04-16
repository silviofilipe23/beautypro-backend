package br.com.beautypro.services.repository;

import br.com.beautypro.models.Service;
import br.com.beautypro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByUserAndOpenAndDateTimeAfter(User user, boolean open, LocalDateTime dateTime);


//    List<Service> findByDateHourBetween(LocalDateTime start, LocalDateTime end);
}
