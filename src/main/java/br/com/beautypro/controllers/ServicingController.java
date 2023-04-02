package br.com.beautypro.controllers;

import br.com.beautypro.models.Servicing;
import br.com.beautypro.payload.request.ServicingRequest;
import br.com.beautypro.payload.request.SupplierRequest;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.ServicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/servicing")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ServicingController {

    @Autowired
    private ServicingService servicingService;

    @GetMapping
    public ResponseEntity<PageableResponse> getListServicing(@Valid @RequestParam int page, @RequestParam int size) {
        PageableResponse servicings = servicingService.listServicings(page, size);
        return new ResponseEntity<>(servicings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createServicing(@Valid @RequestBody ServicingRequest servicingRequest) {

        Servicing servicingSaved = servicingService.createServicing(servicingRequest);
        return new ResponseEntity<>(servicingSaved, HttpStatus.CREATED);

    }
}
