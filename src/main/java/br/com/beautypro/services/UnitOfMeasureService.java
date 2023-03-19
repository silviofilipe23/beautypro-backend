package br.com.beautypro.services;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.UnitOfMeasure;
import br.com.beautypro.repository.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
}
