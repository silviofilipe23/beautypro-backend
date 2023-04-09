package br.com.beautypro.services;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.Servicing;
import br.com.beautypro.models.Supplier;
import br.com.beautypro.models.User;
import br.com.beautypro.payload.request.ServicingRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ServicingRepository;
import br.com.beautypro.services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicingService {

    @Autowired
    private ServicingRepository servicingRepository;

    @Autowired
    private UserRepository userRepository;

    public Servicing createServicing(ServicingRequest servicingRequest) {
        Servicing servicing = new Servicing();
        servicing.setDescription(servicingRequest.getDescription());
        servicing.setPostService(servicingRequest.getPostService());
        servicing.setPreService(servicingRequest.getPreService());
        servicing.setAverageTime(servicingRequest.getAverageTime());
        servicing.setPrice(servicingRequest.getPrice());
        servicing.setReturnDays(servicingRequest.getReturnDays());
        servicing.setActive(true);

        Set<User> userList = new HashSet<>();

        for (Long userId : servicingRequest.getProfessionalList()) {
            Optional<User> user = userRepository.findById(userId);

            user.ifPresent(value -> userList.add(value));
        }

        servicing.setProfessionalList(userList);


        Servicing servicingSaved = servicingRepository.save(servicing);

        return servicingSaved;
    }

    public PageableResponse listServicings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Servicing> servicingResponse = servicingRepository.findAll(pageable);
        List<Servicing> servicingList = servicingResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(servicingList);
        response.setPages(servicingResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(servicingResponse.getTotalElements());

        return response;
    }

    public Optional<Servicing> getServicingById(Long id) {
        return servicingRepository.findById(id);
    }

    public Servicing updateServicing(Servicing servicingRequest) {
        return servicingRepository.save(servicingRequest);
    }

    public Servicing getServicings(Long id) {
        return servicingRepository.findById(id).orElse(null);
    }

    public void deleteServicing(Long id) {
        servicingRepository.deleteById(id);
    }



}
