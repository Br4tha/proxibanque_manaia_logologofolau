package com.proxibanque.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CompteDTO {
    private Long numeroCompte;
    private double solde;
    private Date dateOuverture;
}
