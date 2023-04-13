package br.com.beautypro.services.repository;

import br.com.beautypro.models.City;
import br.com.beautypro.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByUf(State uf);
}
