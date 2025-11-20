package com.proxibanque.service;

import com.proxibanque.dto.ClientDTO;
import com.proxibanque.dto.DTOs;
import com.proxibanque.entity.Client;
import com.proxibanque.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DTOs mapper;

    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(mapper::toClientDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(mapper::toClientDTO)
                .orElse(null);
    }

    @Transactional
    public ClientDTO createClient(Client client) {
        Client savedClient = clientRepository.save(client);
        return mapper.toClientDTO(savedClient);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
