package com.proxibanque.dto;

import lombok.Data;
import java.util.List;

@Data
public class ConseillerDTO {
    private Long id;
    private String nom;
    private String prenom;
    private List<ClientDTO> clients;
}
