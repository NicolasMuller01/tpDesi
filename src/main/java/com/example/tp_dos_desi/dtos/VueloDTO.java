package com.example.tp_dos_desi.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Vuelo;

public class VueloDTO {
    Vuelo vuelo;

    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    String fecha;
    String numeroVuelo;
    String estado;
    String tipoVuelo;
    Integer capacidad;
    Integer capacidadTotal;

    public VueloDTO(Vuelo v, String tipoVuelo) {
        this.id = v.getId();
        this.vuelo = v;
        this.fecha = v.getFecha().toString();
        this.numeroVuelo = v.getNumeroVuelo();
        this.estado = v.getEstado();
        this.tipoVuelo = tipoVuelo;
        Integer i = 0;
        for (Asiento a : vuelo.getAsientos()) {
            if (a.isDisponible()) {
                i++;
            }
        }
        this.capacidad = i;
        this.capacidadTotal = v.getAvion().getCapacidad();
    }

    public Integer getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(Integer capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha.toString();
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public String getTipoVuelo() {
        return this.tipoVuelo;
    }

    public void setTipoVuelo(String tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public static List<VueloDTO> filtrarObjetos(String fecha, String origen, String destino, String vuelo,
            List<Vuelo> vuelos) {

        List<Vuelo> vuelosFiltrados = new ArrayList<>();

        for (Vuelo v : vuelos) {

            if (vuelo.equals("Internacional")) {
                if (v.getFecha().toString().equals(fecha) || fecha.equals("Todos")) {
                    if (v.getCiudadOrigen().getNombre().equals(origen) || origen.equals("Todos")) {
                        if (v.getCiudadDestino().getNombre().equals(destino) || destino.equals("Todos")) {
                            vuelosFiltrados.add(v);
                        }
                    }
                }
            }

            if (vuelo.equals("Todos")) {
                if (v.getFecha().toString().equals(fecha) || fecha.equals("Todos")) {
                    if (v.getCiudadOrigen().getNombre().equals(origen) || origen.equals("Todos")) {
                        if (v.getCiudadDestino().getNombre().equals(destino) || destino.equals("Todos")) {
                            vuelosFiltrados.add(v);
                        }
                    }
                }
            }

            if (vuelo.equals("Nacional")) {
                if (v.getFecha().toString().equals(fecha) || fecha.equals("Todos")) {
                    if (v.getCiudadOrigen().getNombre().equals(origen) || origen.equals("Todos")) {
                        if (v.getCiudadDestino().getNombre().equals(destino) || destino.equals("Todos")) {
                            vuelosFiltrados.add(v);
                        }
                    }
                }
            }

        }

        List<VueloDTO> vuelosFinales = new ArrayList<>();
        for (Vuelo v : vuelosFiltrados) {
            String tipoVuelo = new String();

            if (v.getCiudadOrigen().getPais().equals(v.getCiudadDestino().getPais())) {
                tipoVuelo = "Nacional";
            } else {
                tipoVuelo = "Internacional";
            }

            vuelosFinales.add(new VueloDTO(v, tipoVuelo));
        }

        return vuelosFinales;
    }

}