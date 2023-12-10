package com.example.tp_dos_desi.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    private String nombreAvion;

    public String getNombreAvion() {
        return nombreAvion;
    }

    public void setNombreAvion(String nombreAvion) {
        this.nombreAvion = nombreAvion;
    }

    @Column(unique = true, nullable = false)
    private String numeroAvion;

    public String getNumeroAvion() {
        return numeroAvion;
    }

    public void setNumeroAvion(String numeroAvion) {
        this.numeroAvion = numeroAvion;
    }

    @Column(nullable = false)
    private Integer numeroFilas;

    public Integer getNumeroFilas() {
        return numeroFilas;
    }

    public void setNumeroFilas(Integer numeroFilas) {
        this.numeroFilas = numeroFilas;
    }

    @Column(nullable = false)
    private Integer numeroAsientos;

    public Integer getNumeroAsientos() {
        return numeroAsientos;
    }

    public void setNumeroAsientos(Integer numeroAsientos) {
        this.numeroAsientos = numeroAsientos;
    }

    public Integer getCapacidad() {
        return (Integer) this.numeroAsientos * this.numeroFilas;
    }

    @OneToMany(mappedBy = "avion")
    @Column(nullable = false)
    private List<Vuelo> vuelos;
}