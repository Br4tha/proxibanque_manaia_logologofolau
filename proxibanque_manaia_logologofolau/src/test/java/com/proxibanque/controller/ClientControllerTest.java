package com.proxibanque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxibanque.dto.ClientDTO;
import com.proxibanque.entity.Client;
import com.proxibanque.entity.Conseiller;
import com.proxibanque.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testGetAllClients() throws Exception {
        ClientDTO client1 = new ClientDTO();
        client1.setId(1L);
        client1.setNom("Martin");
        List<ClientDTO> clients = Arrays.asList(client1);

        when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Martin"));
    }

    @Test
    public void testCreateClient() throws Exception {
        Conseiller conseiller = new Conseiller(1L, "Durand", "Paul", null);
        Client clientToCreate = new Client(null, "Dubois", "Marc", "22 avenue de l'Opéra", "75001", "Paris", "0987654321", conseiller, null);
        
        ClientDTO clientCreatedDTO = new ClientDTO();
        clientCreatedDTO.setId(1L);
        clientCreatedDTO.setNom("Dubois");
        clientCreatedDTO.setComptes(new ArrayList<>());


        when(clientService.createClient(any(Client.class))).thenReturn(clientCreatedDTO);

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Dubois"));
    }
}
