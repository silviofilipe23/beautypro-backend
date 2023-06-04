package br.com.beautypro.services;

import br.com.beautypro.models.User;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public PageableResponse getAllServices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<br.com.beautypro.models.Service> productResponse = serviceRepository.findAll(pageable);


        List<br.com.beautypro.models.Service> serviceList = productResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(serviceList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    public PageableResponse getAllServicesFilter(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {

        Pageable pageable = PageRequest.of(page, size);
        Page<br.com.beautypro.models.Service> productResponse = serviceRepository.findByDateTimeBetweenOrderByDateTimeAsc(startDate, endDate, pageable);


        List<br.com.beautypro.models.Service> serviceList = productResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(serviceList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    public List<br.com.beautypro.models.Service> getServiceByUserAndOpenAndDateTimeAfter(User user, boolean open, LocalDateTime dateTime) {
        return serviceRepository.findByUserAndOpenAndDateTimeAfter(user, open, dateTime);
    }


    public br.com.beautypro.models.Service createService(br.com.beautypro.models.Service serviceRequest) {
        return serviceRepository.save(serviceRequest);
    }

    public Optional<br.com.beautypro.models.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public br.com.beautypro.models.Service updateService(br.com.beautypro.models.Service serviceRequest) {
        return serviceRepository.save(serviceRequest);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
