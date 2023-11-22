package br.com.beautypro.services;

import br.com.beautypro.models.UnitOfMeasure;
import br.com.beautypro.services.repository.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitOfMeasureService {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public List<UnitOfMeasure> getAllUnitOfMeasures() {
        return unitOfMeasureRepository.findAll();
    }

    public Optional<UnitOfMeasure> getUnitOfMeasureById(Long id) {
        return unitOfMeasureRepository.findById(id);
    }

    public UnitOfMeasure saveUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        return unitOfMeasureRepository.save(unitOfMeasure);
    }

    public void deleteUnitOfMeasure(Long id) {
        unitOfMeasureRepository.deleteById(id);
    }

    public static class UserService {
    }
}
