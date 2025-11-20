package com.proxibanque.controller;

import com.proxibanque.dto.ConseillerDTO;
import com.proxibanque.entity.Conseiller;
import com.proxibanque.service.ConseillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conseillers")
public class ConseillerController {

    @Autowired
    private ConseillerService conseillerService;

    @GetMapping
    public List<ConseillerDTO> getAllConseillers() {
        return conseillerService.getAllConseillers();
    }



    @GetMapping("/{id}")
    public ResponseEntity<ConseillerDTO> getConseillerById(@PathVariable Long id) {
        ConseillerDTO conseiller = conseillerService.getConseillerById(id);
        return conseiller != null ? ResponseEntity.ok(conseiller) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConseillerDTO createConseiller(@RequestBody Conseiller conseiller) {
        return conseillerService.createConseiller(conseiller);
    }

    @DeleteMapping("/{id}")
    public void deleteConseiller(@PathVariable Long id) {
        conseillerService.deleteConseiller(id);
    }
}
