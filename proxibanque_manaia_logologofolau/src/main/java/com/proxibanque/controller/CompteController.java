package com.proxibanque.controller;

import com.proxibanque.dto.CompteDTO;
import com.proxibanque.entity.Compte;
import com.proxibanque.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @GetMapping
    public List<CompteDTO> getAllComptes() {
        return compteService.getAllComptes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDTO> getCompteById(@PathVariable Long id) {
        CompteDTO compte = compteService.getCompteById(id);
        return compte != null ? ResponseEntity.ok(compte) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompteDTO createCompte(@RequestBody Compte compte) {
        return compteService.createCompte(compte);
    }

    @DeleteMapping("/{id}")
    public void deleteCompte(@PathVariable Long id) {
        compteService.deleteCompte(id);
    }
}
