package com.example.tp_dos_desi.controller;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.service.AsientoService;
import com.example.tp_dos_desi.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

// AsientoController.java
@Controller
public class AsientoController {

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private VueloService vueloService;


    @GetMapping("/asientos/{numeroVuelo}")
    public String mostrarAsientos(@PathVariable String numeroVuelo, Model model) {

        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);

        model.addAttribute("vuelo", vuelo);
        model.addAttribute("asientosDisponibles", vuelo.getAsientos());
        return "asientos";
    }

    @PostMapping("/asientos/{numeroVuelo}/{numeroAsiento}")
    public String reservarAsiento(@PathVariable String numeroVuelo, @PathVariable String numeroAsiento, Model model) {
        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);
        Asiento asiento = asientoService.buscarPorNumeroAsiento(numeroAsiento);
        if (asiento.isDisponible()) {
            asiento.setDisponible(false);
            asientoService.guardar(asiento);
            model.addAttribute("vuelo", vuelo);
            model.addAttribute("asiento", asiento);
            return "reserva";
        } else {
            model.addAttribute("error", "El asiento no está disponible.");
            return "asientos";
        }
    }
}
