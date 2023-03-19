package br.com.beautypro.controllers;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.Supplier;
import br.com.beautypro.payload.request.ClientRequest;
import br.com.beautypro.payload.request.SupplierRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.repository.ClientRepository;
import br.com.beautypro.repository.SupplierRepository;
import br.com.beautypro.services.ClientService;
import br.com.beautypro.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/suppliers")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class SupplierController {


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService supplierService;


    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers(@Valid @RequestParam int page, @RequestParam int size) {
        List<Supplier> suppliers = supplierService.getAllSuppliers(page,size);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {

        if (supplierRepository.existsByEmail(supplierRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email já cadastrado!"));
        }

        if (supplierRepository.existsByCnpj(supplierRequest.getCnpj())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("CNPJ já cadastrado!"));
        }

        Supplier savedSupplier = supplierService.createSupplier(supplierRequest);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }


}
