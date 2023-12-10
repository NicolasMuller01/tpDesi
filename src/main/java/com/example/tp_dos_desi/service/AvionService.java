package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Avion;
import com.example.tp_dos_desi.repository.AvionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvionService {

    @Autowired
    private AvionRepository avionRepository;

    public Optional<Avion> buscarPorId(Long id) {
        return this.avionRepository.findById(id);
    }

    public Avion buscarPorNumeroAvion(String numeroAvion) {
        return this.avionRepository.findByNumeroAvion(numeroAvion);
    }

    public Avion guardar(Avion avion) {
        return this.avionRepository.save(avion);
    }

    public List<Avion> buscarTodos() {
        return this.avionRepository.findAll();
    }

}
