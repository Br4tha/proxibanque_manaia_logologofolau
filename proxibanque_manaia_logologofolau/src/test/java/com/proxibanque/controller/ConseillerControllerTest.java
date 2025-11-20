package com.proxibanque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxibanque.dto.ConseillerDTO;
import com.proxibanque.entity.Conseiller;
import com.proxibanque.service.ConseillerService;
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
public class ConseillerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConseillerService conseillerService;

    @InjectMocks
    private ConseillerController conseillerController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(conseillerController).build();
    }

    @Test
    public void testGetAllConseillers() throws Exception {
        ConseillerDTO conseiller1 = new ConseillerDTO();
        conseiller1.setId(1L);
        conseiller1.setNom("Durand");
        
        ConseillerDTO conseiller2 = new ConseillerDTO();
        conseiller2.setId(2L);
        conseiller2.setNom("Dupont");

        List<ConseillerDTO> conseillers = Arrays.asList(conseiller1, conseiller2);

        when(conseillerService.getAllConseillers()).thenReturn(conseillers);

        mockMvc.perform(get("/conseillers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nom").value("Durand"));
    }

    @Test
    public void testGetConseillerById() throws Exception {
        ConseillerDTO conseiller = new ConseillerDTO();
        conseiller.setId(1L);
        conseiller.setNom("Durand");

        when(conseillerService.getConseillerById(1L)).thenReturn(conseiller);

        mockMvc.perform(get("/conseillers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Durand"));
    }

    @Test
    public void testCreateConseiller() throws Exception {
        Conseiller conseillerToCreate = new Conseiller(null, "Lefevre", "Luc", null);
        
        ConseillerDTO conseillerCreatedDTO = new ConseillerDTO();
        conseillerCreatedDTO.setId(1L);
        conseillerCreatedDTO.setNom("Lefevre");
        conseillerCreatedDTO.setPrenom("Luc");
        conseillerCreatedDTO.setClients(new ArrayList<>());

        when(conseillerService.createConseiller(any(Conseiller.class))).thenReturn(conseillerCreatedDTO);

        mockMvc.perform(post("/conseillers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conseillerToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Lefevre"));
    }

    @Test
    public void testDeleteConseiller() throws Exception {
        mockMvc.perform(delete("/conseillers/1"))
                .andExpect(status().isOk());
    }
}
