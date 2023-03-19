package br.com.beautypro.controllers;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.PageableData;
import br.com.beautypro.models.Product;
import br.com.beautypro.payload.request.ClientRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.repository.ClientRepository;
import br.com.beautypro.repository.UserRepository;
import br.com.beautypro.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<Client>> getAllClients(@Valid @RequestParam int page, @RequestParam int size) {
        List<Client> clients = clientService.listClients(page, size);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequest clientDTO) {

        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Cliente já cadastrado!"));
        }

        Client savedClient = clientService.createClient(clientDTO);

        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }


}
