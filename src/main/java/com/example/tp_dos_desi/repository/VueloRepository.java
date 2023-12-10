package com.example.tp_dos_desi.repository;

import com.example.tp_dos_desi.model.Vuelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Vuelo findByNumeroVuelo(String numeroVuelo);

}
