package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // LOGIKA
    public Client createClient(final Client client) {

        if (isClientInvalid(client)) {
            throw new IllegalArgumentException();
        }
        return clientRepository.save(client);
    }

    private boolean isClientInvalid(final Client client) {
        return client.getFirstName().length() == 0;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClient(final Long clientId) {
        return clientRepository.findById(clientId);
    }
}
