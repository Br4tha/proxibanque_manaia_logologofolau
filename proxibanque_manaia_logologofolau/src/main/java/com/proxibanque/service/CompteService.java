package com.proxibanque.service;

import com.proxibanque.dto.CompteDTO;
import com.proxibanque.dto.DTOs;
import com.proxibanque.entity.Client;
import com.proxibanque.entity.Compte;
import com.proxibanque.repository.ClientRepository;
import com.proxibanque.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private DTOs mapper;

    @Transactional(readOnly = true)
    public List<CompteDTO> getAllComptes() {
        return compteRepository.findAll().stream()
                .map(mapper::toCompteDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CompteDTO getCompteById(Long id) {
        return compteRepository.findById(id)
                .map(mapper::toCompteDTO)
                .orElse(null);
    }

    @Transactional
    public CompteDTO createCompte(Compte compte) {
        if (compte.getClient() == null || compte.getClient().getId() == null) {
            throw new IllegalArgumentException("L'ID du client est requis pour créer un compte.");
        }
        Client client = clientRepository.findById(compte.getClient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID : " + compte.getClient().getId()));
        compte.setClient(client);
        
        Compte savedCompte = compteRepository.save(compte);
        return mapper.toCompteDTO(savedCompte);
    }

    public void deleteCompte(Long id) {
        compteRepository.deleteById(id);
    }
}
