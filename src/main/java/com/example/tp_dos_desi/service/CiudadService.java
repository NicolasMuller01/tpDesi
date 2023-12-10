package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Ciudad;

import com.example.tp_dos_desi.repository.CiudadRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public Ciudad buscarPorNombreyPais(String nombre, String pais) {
        return this.ciudadRepository.findByNombreAndPais(nombre, pais);
    }

    public Ciudad guardar(Ciudad asiento) {
        return this.ciudadRepository.save(asiento);
    }

    public List<Ciudad> buscarTodos() {
        return this.ciudadRepository.findAll();
    }

    public Optional<Ciudad> buscarPorId(Long id) {
        return this.ciudadRepository.findById(id);
    }

}
