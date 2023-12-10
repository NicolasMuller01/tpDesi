package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VueloService {

    @Autowired
    private VueloRepository vueloRepository;

    public List<Vuelo> buscarTodos() {
        return vueloRepository.findAll();
    }

    public Vuelo buscarPorNumeroVuelo(String numeroVuelo) {
        return vueloRepository.findByNumeroVuelo(numeroVuelo);
    }

    public Vuelo crearVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    public List<Vuelo> buscarPorFecha(LocalDate fecha) {

        List<Vuelo> vuelosFiltrados = new ArrayList<Vuelo>();

        for (Vuelo vuelo : vueloRepository.findAll()) {
            if (vuelo.getFecha().isEqual(fecha)) {
                vuelosFiltrados.add(vuelo);
            }
        }

        return vuelosFiltrados;
    }

    public Optional<Vuelo> buscarPorId(Long id) {
        return vueloRepository.findById(id);
    }

}
