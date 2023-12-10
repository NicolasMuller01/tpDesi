package com.example.tp_dos_desi.controller;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Cliente;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.repository.ClienteRepository;
import com.example.tp_dos_desi.service.ClienteService;
import com.example.tp_dos_desi.service.VueloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VueloService vueloService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/comprarAsiento")
    public String buscarCliente(@RequestParam("dni") String dni, Model model) {
        Cliente cliente = clienteService.buscarPorDni(dni);

        List<Vuelo> vuelos = this.vueloService.buscarTodos();

        List<LocalDate> listaFechaSalida = new ArrayList<LocalDate>();
        List<String> listaCiudadOrigen = new ArrayList<String>();
        List<String> listaCiudadDestino = new ArrayList<String>();
        List<String> listaTipoVuelo = new ArrayList<String>();

        for (Vuelo vuelo : vuelos) {
            listaCiudadOrigen.add(vuelo.getCiudadOrigen().getNombre());
            listaCiudadDestino.add(vuelo.getCiudadDestino().getNombre());
            listaFechaSalida.add(vuelo.getFecha());

            String tipoVuelo = new String();

            if (vuelo.getCiudadDestino().getPais().equals(vuelo.getCiudadOrigen().getPais())) {
                tipoVuelo = "Nacional";
            } else {
                tipoVuelo = "Internacional";
            }
            listaTipoVuelo.add(tipoVuelo);
        }

        Set<String> filtroCiudadOrigenSet = new HashSet<>(listaCiudadOrigen);
        Set<String> filtroCiudadDestinoSet = new HashSet<>(listaCiudadDestino);
        Set<String> filtroTipoVueloSet = new HashSet<>(listaTipoVuelo);
        Set<LocalDate> filtroFechaSalidaSet = new HashSet<>(listaFechaSalida);

        List<String> filtroCiudadOrigen = new ArrayList<String>(filtroCiudadOrigenSet);
        List<String> filtroCiudadDestino = new ArrayList<String>(filtroCiudadDestinoSet);
        List<String> filtroTipoVuelo = new ArrayList<String>(filtroTipoVueloSet);
        List<LocalDate> filtroFechaSalida = new ArrayList<LocalDate>(filtroFechaSalidaSet);

        if (cliente != null) {
            model.addAttribute("filtroFechaSalida", filtroFechaSalida);
            model.addAttribute("filtroCiudadOrigen", filtroCiudadOrigen);
            model.addAttribute("filtroCiudadDestino", filtroCiudadDestino);
            model.addAttribute("filtroTipoVuelo", filtroTipoVuelo);
            model.addAttribute("cliente", cliente);
            return "comprarTicket";
        } else {
            model.addAttribute("error", "El cliente no está registrado.");
            return "index";
        }
    }

    @GetMapping("/resultado")
    public String mostrarResultado(@RequestParam("dni") String dni,@RequestParam("fecha") String fecha, @RequestParam("origen") String origen,
            @RequestParam("destino") String destino, @RequestParam("vuelo") String vuelo,
            Model model) {
        List<VueloDTO> objetosFiltrados = filtrarObjetos(fecha, origen, destino, vuelo);
        // Otros procesamientos y filtrados

        model.addAttribute("objetosFiltrados", objetosFiltrados);
        model.addAttribute("dni", dni);
        // Otros atributos del modelo

        return "vuelos";
    }

    private List<VueloDTO> filtrarObjetos(String fecha, String origen, String destino, String vuelo) {
        List<Vuelo> vuelos = this.vueloService.buscarTodos();

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

class VueloDTO {
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

}