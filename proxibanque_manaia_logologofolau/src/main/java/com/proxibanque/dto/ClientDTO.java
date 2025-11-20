package com.proxibanque.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private List<CompteDTO> comptes;
}
