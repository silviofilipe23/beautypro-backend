package br.com.beautypro.controllers;

import br.com.beautypro.models.*;
import br.com.beautypro.payload.request.*;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public ResponseEntity<PageableResponse> getAllServices(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDate,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDate,
            @RequestParam(value = "open", required = false) boolean open
    ) {

        PageableResponse response;
        if (startDate != null) {
            response = serviceService.getAllServicesFilter(page, size, startDate, endDate, open);
        } else {
            response = serviceService.getAllServices(page, size);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<PageableResponse> getAllServicesFilterDesc(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String paymentType,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) String professionalName) {

        PageableResponse response;
        response = serviceService.getAllServicesFilterDesc(page, size, startDate, endDate, paymentType, clientName, serviceName, professionalName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pie-chart")
    public ResponseEntity<List<StatusCount>> getStatusCountsInCurrentMonth() {

        List<StatusCount> response = serviceService.getStatusCountsInCurrentMonth();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/servicings-chart")
    public ResponseEntity<List<ServicingCountDTO>> getServicingCountsInCurrentMonth() {

        List<ServicingCountDTO> response = serviceService.getServicingCountsInCurrentMonth();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/price-chart")
    public ResponseEntity<List<MonthlyServiceSumDTO>> getMonthlyServiceSums() {

        List<MonthlyServiceSumDTO> response = serviceService.getMonthlyServiceSums();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/daily-chart")
    public ResponseEntity<List<DailyServiceCountDTO>> getDailyServiceCountsInCurrentMonth() {

        List<DailyServiceCountDTO> response = serviceService.getDailyServiceCountsInCurrentMonth();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/available-time")
    public ResponseEntity<?> getAvailableAppointmentTime(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDate

    ) {
        int[] response = serviceService.getAppointmentsAvailable(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signature")
    public ResponseEntity<?> saveBase64Signature(
            @RequestParam Long id,
            @RequestBody String base64Signature
    ) {

        Optional<Service> existingServicing = serviceService.getServiceById(id);

        if (existingServicing.isPresent()) {
            Service savedService = serviceService.saveBase64Signature(id, base64Signature);
            return new ResponseEntity<>(savedService, HttpStatus.CREATED);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Serviço não encontrado!"));
        }
    }

    @GetMapping("/by-client")
    public ResponseEntity<PageableResponse>  listServicesByClientId(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Long id
    ) {
        Client client = new Client();
        client.setId(id);

        PageableResponse response = serviceService.listServicesByClients(page, size, client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createService(@Valid @RequestBody Service serviceRequest) throws MessagingException {

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
    public ResponseEntity<?> updateService(@Valid @PathVariable("id") Long id, @RequestBody Service serviceRequest) throws MessagingException {

        Optional<Service> serviceExists = serviceRepository.findById(id);

        if (serviceExists.isPresent()) {
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
