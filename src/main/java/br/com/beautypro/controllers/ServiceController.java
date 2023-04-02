package br.com.beautypro.controllers;

import br.com.beautypro.models.Product;
import br.com.beautypro.models.Service;
import br.com.beautypro.models.Supplier;
import br.com.beautypro.models.UnitOfMeasure;
import br.com.beautypro.payload.request.ProductRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.repository.ClientRepository;
import br.com.beautypro.repository.ServiceRepository;
import br.com.beautypro.repository.ServicingRepository;
import br.com.beautypro.repository.UserRepository;
import br.com.beautypro.services.ClientService;
import br.com.beautypro.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/services")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServicingRepository servicingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public ResponseEntity<PageableResponse> getAllServices( @RequestParam int page, @RequestParam int size) {
        PageableResponse response = serviceService.getAllServices(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createService(@Valid @RequestBody ProductRequest productRequest) {

//        Client cliente = clientRepository.findById(clienteId)
//                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


}
