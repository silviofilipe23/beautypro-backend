package br.com.beautypro.controllers;

import br.com.beautypro.models.*;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.ProductService;
import br.com.beautypro.services.ServicingService;
import br.com.beautypro.services.StockMovementService;
import br.com.beautypro.services.repository.ClientRepository;
import br.com.beautypro.services.repository.ServiceRepository;
import br.com.beautypro.services.repository.ServicingRepository;
import br.com.beautypro.services.repository.UserRepository;
import br.com.beautypro.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private ServicingService servicingService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockMovementService stockMovementService;

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
    public ResponseEntity<?> createService(@Valid @RequestBody Service serviceRequest) {

        if (!clientRepository.existsByEmail(serviceRequest.getClient().getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Cliente não encontrado!"));
        }

        if (!userRepository.existsByEmail(serviceRequest.getUser().getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Usuário não encontrado!"));
        }

        if (!servicingRepository.existsById(serviceRequest.getServicing().getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Serviço não encontrado!"));
        }

        Service savedService = serviceService.createService(serviceRequest);

        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@Valid @PathVariable("id") Long id, @RequestBody Service serviceRequest) {

        Optional<Service> serviceExists = serviceRepository.findById(id);

        if (serviceExists.isPresent()) {

            Optional<Servicing> servicing = servicingService.getServicingById(serviceRequest.getServicing().getId());

            if (!serviceRequest.isOpen()) {

                if (servicing.isPresent()) {
                    // Atualiza o estoque dos produtos utilizados
                    for (ConsumedProducts consumedProducts : servicing.get().getServicingProducts()) {
                        int quantityUsed = consumedProducts.getQuantity();
                        if (quantityUsed > 0) {
                            stockMovementService.addMovement(
                                    consumedProducts.getProduct().getId(),
                                    quantityUsed,
                                    MovementType.OUT
                            );
                            consumedProducts.getProduct().setQuantity(consumedProducts.getProduct().getQuantity() - quantityUsed);
                            productService.saveProduct(consumedProducts.getProduct());
                        }
                    }
                }
            }

            Service serviceSaved = serviceService.updateService(serviceRequest);
            return new ResponseEntity<>(serviceSaved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@Valid @PathVariable("id") Long id) {

        Optional<Service> existingServicing = serviceService.getServiceById(id);

        if (existingServicing.isPresent()) {
            serviceService.deleteService(id);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Agendamento cancelado com sucesso!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
