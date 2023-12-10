package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    public Asiento buscarPorNumeroAsiento(String numeroAsiento) {
        return asientoRepository.findAsientoByNumeroAsiento(numeroAsiento);
    }

    public void guardar(Asiento asiento) {
        asientoRepository.save(asiento);
    }

    public Asiento agregarAsiento(Asiento asiento) {
        return this.asientoRepository.save(asiento);
    }

    public Asiento buscarPorId(Long id){
        return this.asientoRepository.getById(id);
    }

}
