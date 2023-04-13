package br.com.beautypro.controllers;

import br.com.beautypro.models.City;
import br.com.beautypro.models.State;
import br.com.beautypro.services.StateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/states")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public List<State> getAllStates() {
        return stateService.getAllStates();
    }

    @GetMapping("/{id}")
    public State getStateById(@PathVariable Long id) {
        return stateService.getStateById(id);
    }

    @GetMapping("/{id}/cities")
    public List<City> getCitiesByStateId(@PathVariable Long id) {
        return stateService.getCitiesByStateId(id);
    }
}
