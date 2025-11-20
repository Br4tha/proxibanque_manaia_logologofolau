package com.proxibanque.service;

import com.proxibanque.dto.ConseillerDTO;
import com.proxibanque.dto.DTOs;
import com.proxibanque.entity.Conseiller;
import com.proxibanque.repository.ConseillerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConseillerService {

    @Autowired
    private ConseillerRepository conseillerRepository;

    @Autowired
    private DTOs mapper;

    @Transactional(readOnly = true)
    public List<ConseillerDTO> getAllConseillers() {
        return conseillerRepository.findAll().stream()
                .map(mapper::toConseillerDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConseillerDTO getConseillerById(Long id) {
        return conseillerRepository.findById(id)
                .map(mapper::toConseillerDTO)
                .orElse(null);
    }

    @Transactional
    public ConseillerDTO createConseiller(Conseiller conseiller) {
        Conseiller savedConseiller = conseillerRepository.save(conseiller);
        return mapper.toConseillerDTO(savedConseiller);
    }

    public void deleteConseiller(Long id) {
        conseillerRepository.deleteById(id);
    }
}
