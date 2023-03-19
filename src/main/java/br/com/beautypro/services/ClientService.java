package br.com.beautypro.services;

import br.com.beautypro.models.Address;
import br.com.beautypro.models.Client;
import br.com.beautypro.payload.request.ClientRequest;
import br.com.beautypro.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> listClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.findAll(pageable);
        List<Client> clientDTO = clients.stream()
                .map(client -> new Client(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.isActive(), client.getAddress()))
                .collect(Collectors.toList());
        return clientDTO;
    }

    public Client createClient(ClientRequest clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setObservations(clientDTO.getObservations());
        client.setActive(true);

        Address address = new Address();

        address.setCep(clientDTO.getCep());
        address.setCity(clientDTO.getCity());
        address.setComplement(clientDTO.getComplement());
        address.setDistrict(clientDTO.getDistrict());
        address.setNumber(clientDTO.getNumber());
        address.setStreet(clientDTO.getStreet());
        address.setState(clientDTO.getState());

        client.setAddress(address);

        Client clientSave = clientRepository.save(client);
        Client clientSaveDTO = new Client(clientSave.getId(), clientSave.getName(), clientSave.getEmail(), clientSave.getPhoneNumber(), address);
        return clientSaveDTO;
    }
}