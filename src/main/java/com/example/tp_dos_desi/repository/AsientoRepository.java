package com.example.tp_dos_desi.repository;

import com.example.tp_dos_desi.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {

    Asiento findAsientoByNumeroAsiento(String numeroAsiento);

}