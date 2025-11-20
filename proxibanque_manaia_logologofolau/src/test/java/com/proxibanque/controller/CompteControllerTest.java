package com.proxibanque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proxibanque.dto.CompteDTO;
import com.proxibanque.entity.Client;
import com.proxibanque.entity.Compte;
import com.proxibanque.service.CompteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CompteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompteService compteService;

    @InjectMocks
    private CompteController compteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(compteController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void testGetAllComptes() throws Exception {
        CompteDTO compte1 = new CompteDTO();
        compte1.setNumeroCompte(1L);
        compte1.setSolde(500.0);
        List<CompteDTO> comptes = Arrays.asList(compte1);

        when(compteService.getAllComptes()).thenReturn(comptes);

        mockMvc.perform(get("/comptes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].solde").value(500.0));
    }

    @Test
    public void testCreateCompte() throws Exception {
        Client client = new Client(1L, "Martin", "Alice", null, null, null, null, null, null);
        Compte compteToCreate = new Compte(null, 1500.0, new Date(), client);
        
        CompteDTO compteCreatedDTO = new CompteDTO();
        compteCreatedDTO.setNumeroCompte(1L);
        compteCreatedDTO.setSolde(1500.0);

        when(compteService.createCompte(any(Compte.class))).thenReturn(compteCreatedDTO);

        mockMvc.perform(post("/comptes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compteToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCompte").value(1L))
                .andExpect(jsonPath("$.solde").value(1500.0));
    }
}
