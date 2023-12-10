package com.example.tp_dos_desi.repository;

import com.example.tp_dos_desi.model.Vuelo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Vuelo findByNumeroVuelo(String numeroVuelo);

}
