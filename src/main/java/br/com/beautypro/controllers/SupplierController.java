package br.com.beautypro.controllers;

import br.com.beautypro.models.Supplier;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.SupplierRepository;
import br.com.beautypro.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    public ResponseEntity<PageableResponse> getAllSuppliers(@Valid @RequestParam int page, @RequestParam int size, @RequestParam(required=false) String name, @RequestParam(required=false) Boolean active) {

        if (name != null && active != null) {
            PageableResponse suppliers = supplierService.listSuppliersFilterNameAndActive(page, size, name, active);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } else if (name != null) {
            PageableResponse suppliers = supplierService.listSuppliersFilter(page, size, name);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } else if (active != null) {
            PageableResponse suppliers = supplierService.listSuppliersFilterActive(page, size, active);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } else {
            PageableResponse suppliers = supplierService.getAllSuppliers(page,size);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierId(@Valid @PathVariable("id") Long id) {
        Optional<Supplier> supplier = supplierService.getSupplierById(id);
        return supplier.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody Supplier supplierRequest) {

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@Valid @PathVariable Long id, @RequestBody Supplier supplierRequest) {

        Optional<Supplier> supplierExists = supplierRepository.findById(id);

        if (supplierExists.isPresent()) {

            Supplier supplier = supplierExists.get();

            supplier.setCnpj(supplierRequest.getCnpj());
            supplier.setCorporateName(supplierRequest.getCorporateName());
            supplier.setName(supplierRequest.getName());
            supplier.setActive(supplierRequest.isActive());
            supplier.setPhoneNumber(supplierRequest.getPhoneNumber());
            supplier.setEmail(supplierRequest.getEmail());
            supplier.setObservations(supplierRequest.getObservations());
            supplier.setAddress(supplierRequest.getAddress());

            Supplier savedSupplier = supplierService.updateSupplier(supplier);
            return new ResponseEntity<>(savedSupplier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@Valid @PathVariable("id") Long id) {
        Optional<Supplier> existingSupplier = supplierService.getSupplierById(id);
        if (existingSupplier.isPresent()) {
            supplierService.deleteSupplier(id);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Fornecedor inativado com sucesso!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
