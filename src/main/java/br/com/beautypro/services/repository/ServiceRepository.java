package br.com.beautypro.services.repository;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.Service;
import br.com.beautypro.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    Page<Service> findByDateTimeBetweenOrderByDateTimeAsc(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

//    Page<Service> findByDateTimeBetweenOrderByDateTimeDesc(
//            @Param("startDate") LocalDateTime startDate,
//            @Param("endDate") LocalDateTime endDate,
//            Pageable pageable
//    );

    // Método personalizado para listar todos ordenados pela maior data de criação
    @Query("SELECT s FROM Service s ORDER BY s.createdDate DESC")
    Page<Service> findAllOrderByCreatedDateDesc(Pageable pageable);

    @Query("SELECT EXTRACT(YEAR FROM s.dateTime) as year, EXTRACT(MONTH FROM s.dateTime) as month, SUM(s.price) as totalSum " +
            "FROM Service s " +
            "WHERE s.finishedDate IS NOT NULL " +
            "GROUP BY EXTRACT(YEAR FROM s.dateTime), EXTRACT(MONTH FROM s.dateTime)")
    List<Object[]> getMonthlyServiceSums();

    //realiza a contagem de atendimentos agrupados por dia para o mês atual
    @Query("SELECT EXTRACT(DAY FROM s.dateTime) as day, COUNT(s) " +
            "FROM Service s " +
            "WHERE EXTRACT(YEAR FROM s.dateTime) = YEAR(CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM s.dateTime) = MONTH(CURRENT_DATE) " +
            "AND s.finishedDate IS NOT NULL " +
            "GROUP BY EXTRACT(DAY FROM s.dateTime)")
    List<Object[]> getDailyServiceCountsInCurrentMonth();

    Page<Service> findAll(Specification<Service> specification, Pageable pageable);

    List<Service> findByDateTimeAfterAndDateTimeBefore(LocalDateTime startDate, LocalDateTime endDate);

    List<Service> findByDateTimeBetweenAndFinishedDateIsNotNull(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<Service> findByDateTimeBetween(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<Service> findByDateTimeBetweenOrderByDateTimeAsc(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
