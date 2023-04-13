package br.com.beautypro.services;

import br.com.beautypro.models.City;
import br.com.beautypro.models.State;
import br.com.beautypro.services.repository.CityRepository;
import br.com.beautypro.services.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StateService {
    @Autowired
    private final StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public State getStateById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State with id " + id + " not found"));
    }

    public List<City> getCitiesByStateId(Long stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new EntityNotFoundException("State with id " + stateId + " not found"));
        return cityRepository.findByUf(state);
    }
}
