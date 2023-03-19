package br.com.beautypro.controllers;

import br.com.beautypro.models.Client;
import br.com.beautypro.payload.request.ClientRequest;
import br.com.beautypro.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/client")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Client> listClients(@RequestParam int page, @RequestParam int size) {
        return clientService.listClients(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Client createClient(@RequestBody ClientRequest clientDTO) {
        return clientService.createClient(clientDTO);
    }


}
