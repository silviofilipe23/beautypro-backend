package br.com.beautypro.controllers;

import br.com.beautypro.models.Address;
import br.com.beautypro.models.Client;
import br.com.beautypro.payload.request.ClientRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ClientRepository;
import br.com.beautypro.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<PageableResponse> getAllClients(@Valid @RequestParam int page, @RequestParam int size, @RequestParam(required=false) String name) {

        if (name == null) {
            PageableResponse clients = clientService.listClients(page, size);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } else {
            PageableResponse clients = clientService.listClientsFilter(page, size, name);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@Valid @PathVariable("id") Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequest clientDTO) {

        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email já cadastrado!"));
        }

        if (clientRepository.existsByCpf(clientDTO.getCpf())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("CPF já cadastrado!"));
        }

        if (clientRepository.existsByRg(clientDTO.getRg())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("RG já cadastrado!"));
        }

        Client savedClient = clientService.createClient(clientDTO);

        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @PathVariable Long id, @RequestBody ClientRequest clientDTO) {

        Optional<Client> clientExists = clientRepository.findById(id);

        if (clientExists.isPresent()) {
            Client client = clientExists.get();

            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setPhoneNumber(clientDTO.getPhoneNumber());
            client.setObservations(clientDTO.getObservations());

            Address address = new Address();

            address.setState(clientDTO.getState());
            address.setStreet(clientDTO.getStreet());
            address.setNumber(clientDTO.getNumber());
            address.setDistrict(clientDTO.getDistrict());
            address.setComplement(clientDTO.getComplement());
            address.setCity(clientDTO.getCity());
            address.setCep(clientDTO.getCep());

            client.setAddress(address);
            clientRepository.save(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@Valid @PathVariable("id") Long id) {
        Optional<Client> existingProduct = clientService.getClientById(id);
        if (existingProduct.isPresent()) {
            clientService.deleteClient(id);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Cliente Inativado com sucesso!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
