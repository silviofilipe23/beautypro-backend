package br.com.beautypro.services.repository;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.Service;
import br.com.beautypro.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByUserAndOpenAndDateTimeAfter(User user, boolean open, LocalDateTime dateTime);

    Page<Service> findByClient(Client client, Pageable pageable);

    Page<Service> findByDateTimeBetweenAndOpenOrderByDateTimeAsc(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("open") boolean open,
            Pageable pageable
    );

    List<Service> findByDateTimeBetweenOrderByDateTimeAsc(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
