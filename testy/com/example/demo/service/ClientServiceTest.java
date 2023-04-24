package com.example.demo.service;

import com.example.demo.exception.InvalidClientDataException;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void createClientThrowsExceptionWhenFirstNameIsEmptyString() {
        // given
        final Client client = new Client();
        client.setFirstName("");

        // when

        // then
        assertThrows(InvalidClientDataException.class, () -> {
           clientService.createClient(client);
        });
    }

    @Test
    public void createClientShouldCreateClientWhenAllPropertiesAreCorrect() {
        // given
        final Client client = new Client();
        client.setFirstName("Arek");
        client.setPostalCode("00-000");

        // when
        when(clientRepository.save(client)).thenReturn(client);

        // then
        final Client result = clientService.createClient(client);
        assertThat(result.getFirstName()).isEqualTo("Arek");
        assertThat(result.getPostalCode()).isNotEmpty();
    }
}
