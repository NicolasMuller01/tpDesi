package com.example.tp_dos_desi.repository;

import com.example.tp_dos_desi.model.Avion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {

    Avion findById(String id);

    Avion findByNumeroAvion(String numeroAvion);
}