package br.com.beautypro.services;

import br.com.beautypro.models.*;
import br.com.beautypro.payload.request.SupplierRequest;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public PageableResponse getAllSuppliers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> supplierResponse = supplierRepository.findAll(pageable);
        List<Supplier> supplierList = supplierResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(supplierList);
        response.setPages(supplierResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(supplierResponse.getTotalElements());

        return response;
    }

    public PageableResponse listSuppliersFilterNameAndActive(int page, int size, String name, boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> supplierResponse = supplierRepository.findByNameContainingIgnoreCaseAndActive(name, active, pageable);
        List<Supplier> supplierList = supplierResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(supplierList);
        response.setPages(supplierResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(supplierResponse.getTotalElements());

        return response;
    }

    public PageableResponse listSuppliersFilterActive(int page, int size, boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> supplierResponse = supplierRepository.findByActive(active,  pageable);
        List<Supplier> supplierList = supplierResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(supplierList);
        response.setPages(supplierResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(supplierResponse.getTotalElements());

        return response;
    }

    public PageableResponse listSuppliersFilter(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> supplierResponse = supplierRepository.findByNameContainingIgnoreCase(name,  pageable);
        List<Supplier> supplierList = supplierResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(supplierList);
        response.setPages(supplierResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(supplierResponse.getTotalElements());

        return response;
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier createSupplier(Supplier supplierRequest) {

        return supplierRepository.save(supplierRequest);

    }

    public Supplier updateSupplier(Supplier supplierRequest) {
        return supplierRepository.save(supplierRequest);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

}
