package com.example.demo.controller;

import com.example.demo.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createClientShouldCreateClientAndReturnCreatedStatus() throws Exception {
        final Client client = new Client();
        client.setFirstName("Arek");
        client.setPostalCode("00-001");

        mockMvc.perform(post("/api/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.firstName").value("Arek")))
                .andExpect((jsonPath("$.postalCode").value("00-001")))
                .andExpect((jsonPath("$.id").exists()));
    }

    @Test
    public void createClientShouldThrowExceptionWhenFistNameIsEmpty() throws Exception {
        final Client client = new Client();
        client.setFirstName("");
        client.setPostalCode("00-001");

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(status().isBadRequest());
    }
}