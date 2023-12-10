package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Avion;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.repository.AvionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvionService {

    @Autowired
    private AvionRepository avionRepository;


    public Avion buscarPorId(String id) {
        return this.avionRepository.findById(id);
    }

    public Avion buscarPorNumeroAvion(String numeroAvion) {
        return this.avionRepository.findByNumeroAvion(numeroAvion);
    }

    public Avion guardar(Avion avion) {
        return this.avionRepository.save(avion);
    }

    public List<Avion> buscarTodos(){
        return this.avionRepository.findAll();
    }
    
}
