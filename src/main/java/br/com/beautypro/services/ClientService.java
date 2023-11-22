package br.com.beautypro.services;

import br.com.beautypro.models.Client;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ClientRepository;
import br.com.beautypro.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public PageableResponse listClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.findAll(pageable);
        List<Client> clientDTO = clients.stream().collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(clientDTO);
        response.setPages(clients.getTotalPages());
        response.setSize(size);
        response.setTotal(clients.getTotalElements());
        return response;
    }

    public PageableResponse listClientsFilter(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.findByNameContainingIgnoreCase(name, pageable);
        List<Client> clientDTO = clients.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(clientDTO);
        response.setPages(clients.getTotalPages());
        response.setSize(size);
        response.setTotal(clients.getTotalElements());
        return response;
    }

    public Client createClient(Client clientDTO) {
        return clientRepository.save(clientDTO);
    }

    public Client updatedClient(Client clientDTO) {
        return clientRepository.save(clientDTO);
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}