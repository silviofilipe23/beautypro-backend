package br.com.beautypro.controllers;

import br.com.beautypro.models.Servicing;
import br.com.beautypro.models.Supplier;
import br.com.beautypro.payload.request.ServicingRequest;
import br.com.beautypro.payload.request.SupplierRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.ServicingService;
import br.com.beautypro.services.repository.ServicingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/servicing")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ServicingController {

    @Autowired
    private ServicingService servicingService;

    @Autowired
    private ServicingRepository servicingRepository;

    @GetMapping
    public ResponseEntity<PageableResponse> getAllServicing(@Valid @RequestParam int page, @RequestParam int size, @RequestParam(required=false) String description, @RequestParam(required=false) Boolean active) {

        if (description != null && active != null) {
            PageableResponse suppliers = servicingService.listServicingFilterDescriptionAndActive(page, size, description, active);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } else if (description != null) {
            PageableResponse suppliers = servicingService.listServicingFilterDescription(page, size, description);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } else if (active != null) {
            PageableResponse suppliers = servicingService.listServicingFilterActive(page, size, active);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } else {
            PageableResponse servicing = servicingService.listServicings(page, size);
            return new ResponseEntity<>(servicing, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicing> getServicingId(@Valid @PathVariable("id") Long id) {
        Optional<Servicing> supplier = servicingService.getServicingById(id);
        return supplier.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createServicing(@Valid @RequestBody Servicing servicingRequest) {

        Servicing servicingSaved = servicingService.createServicing(servicingRequest);
        return new ResponseEntity<>(servicingSaved, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServicing(@Valid @PathVariable("id") Long id, @RequestBody Servicing servicingRequest) {

        Optional<Servicing> servicingExists = servicingRepository.findById(id);

        if (servicingExists.isPresent()) {
            Servicing servicingSaved = servicingService.updateServicing(servicingRequest);
            return new ResponseEntity<>(servicingSaved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServicing(@Valid @PathVariable("id") Long id) {

        Optional<Servicing> existingServicing = servicingService.getServicingById(id);

        if (existingServicing.isPresent()) {
            servicingService.deleteServicing(id);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Serviço inativado com sucesso!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
