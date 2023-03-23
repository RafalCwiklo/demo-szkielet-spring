package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    //public ResponseEntity<Client> createClient(@RequestBody final Report report) {
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody final Client client) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.createClient(client));
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.getAllClients());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClient(@PathVariable("clientId") final Long clientId) throws ChangeSetPersister.NotFoundException {
        final Client client = clientService.getClient(clientId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(client);
    }

}
