package com.example.tp_dos_desi.repository;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {

    Asiento findAsientoByNumeroAsiento(String numeroAsiento);

}