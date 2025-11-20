package com.proxibanque.dto;

import com.proxibanque.entity.Client;
import com.proxibanque.entity.Compte;
import com.proxibanque.entity.Conseiller;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.Collections;

@Component
public class DTOs {

    public CompteDTO toCompteDTO(Compte compte) {
        if (compte == null) return null;
        CompteDTO dto = new CompteDTO();
        dto.setNumeroCompte(compte.getNumeroCompte());
        dto.setSolde(compte.getSolde());
        dto.setDateOuverture(compte.getDateOuverture());
        return dto;
    }

    public ClientDTO toClientDTO(Client client) {
        if (client == null) return null;
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setAdresse(client.getAdresse());
        dto.setCodePostal(client.getCodePostal());
        dto.setVille(client.getVille());
        dto.setTelephone(client.getTelephone());
        if (client.getComptes() != null) {
            dto.setComptes(client.getComptes().stream().map(this::toCompteDTO).collect(Collectors.toList()));
        } else {
            dto.setComptes(Collections.emptyList());
        }
        return dto;
    }

    public ConseillerDTO toConseillerDTO(Conseiller conseiller) {
        if (conseiller == null) return null;
        ConseillerDTO dto = new ConseillerDTO();
        dto.setId(conseiller.getId());
        dto.setNom(conseiller.getNom());
        dto.setPrenom(conseiller.getPrenom());
        if (conseiller.getClients() != null) {
            dto.setClients(conseiller.getClients().stream().map(this::toClientDTO).collect(Collectors.toList()));
        } else {
            dto.setClients(Collections.emptyList());
        }
        return dto;
    }
}
