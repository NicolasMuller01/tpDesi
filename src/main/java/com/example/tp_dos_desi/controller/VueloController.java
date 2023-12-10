package com.example.tp_dos_desi.controller;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Avion;
import com.example.tp_dos_desi.model.Ciudad;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.repository.VueloRepository;
import com.example.tp_dos_desi.service.AsientoService;
import com.example.tp_dos_desi.service.AvionService;
import com.example.tp_dos_desi.service.CiudadService;
import com.example.tp_dos_desi.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VueloController {

    @Autowired
    private VueloService vueloService;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private AvionService avionService;

    @GetMapping("/vuelos")
    public String mostrarVuelos(Model model) {
        List<Vuelo> vuelos = vueloService.buscarTodos();

        model.addAttribute("vuelos", vuelos);
        return "vuelos";
    }

    @GetMapping("/asientos-vuelo/{numeroVuelo}")
    @ResponseBody
    public List<Asiento> mostrarAsientos(@PathVariable String numeroVuelo) {
        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);

        List<Asiento> asientosDisponibles = new ArrayList<>();

        for (Asiento asiento : vuelo.getAsientos()) {
            if (asiento.isDisponible()) {
                asientosDisponibles.add(asiento);
            }
        }

        return asientosDisponibles;
    }

    @GetMapping("/precio-vuelo/{numeroVuelo}/{numeroAsiento}")
    @ResponseBody
    public double mostrarPrecio(@PathVariable String numeroVuelo, @PathVariable String numeroAsiento) {
        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);
        Asiento asiento = asientoService.buscarPorNumeroAsiento(numeroAsiento);
        return vuelo.getPrecio() + asiento.getPrecio();
    }

    @PostMapping("/vuelos")
    public ResponseEntity<Vuelo> crearVuelo(@RequestBody Vuelo vuelo) {

        vuelo.setEstado("Normal");

        // Verificar si el numero de vuelo existe
        Vuelo vueloExistente = this.vueloService.buscarPorNumeroVuelo(vuelo.getNumeroVuelo());

        if (vueloExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vuelo);
        }

        // Verificar si el avion existe
        Avion avionDelVuelo = vuelo.getAvion();

        if (avionDelVuelo != null) {
            Avion avionDB = avionService.buscarPorNumeroAvion(avionDelVuelo.getNumeroAvion());

            if (avionDB == null) {
                avionDB = avionService.guardar(avionDelVuelo);
            }

            vuelo.setAvion(avionDB);
        }

        // Verificar si el avion esta disponible ese dia
        List<Vuelo> vuelosEnLaFecha = this.vueloService.buscarPorFecha(vuelo.getFecha());
        for (Vuelo v : vuelosEnLaFecha) {
            if (v.getAvion().getId() == vuelo.getAvion().getId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vuelo);
            }
        }

        // Verificar si las ciudades de origen y destino existen en la base de datos
        Ciudad ciudadOrigen = vuelo.getCiudadOrigen();
        Ciudad ciudadDestino = vuelo.getCiudadDestino();

        if (ciudadOrigen != null && ciudadDestino != null) {
            Ciudad ciudadOrigenDB = ciudadService.buscarPorNombreyPais(ciudadOrigen.getNombre(),
                    ciudadOrigen.getPais());
            Ciudad ciudadDestinoDB = ciudadService.buscarPorNombreyPais(ciudadDestino.getNombre(),
                    ciudadDestino.getPais());

            if (ciudadOrigenDB == null) {
                ciudadOrigenDB = ciudadService.guardar(ciudadOrigen);
            }

            if (ciudadDestinoDB == null) {
                ciudadDestinoDB = ciudadService.guardar(ciudadDestino);
            }

            vuelo.setCiudadOrigen(ciudadOrigenDB);
            vuelo.setCiudadDestino(ciudadDestinoDB);
        }

        try {
            Vuelo nuevoVuelo = vueloService.crearVuelo(vuelo);
            Avion avion = nuevoVuelo.getAvion();
            Integer capacidad = avion.getCapacidad();

            for (int i = 0; i <= capacidad; i++) {
                Asiento nuevoAsiento = new Asiento(i, nuevoVuelo, true, nuevoVuelo.getPrecio());
                this.asientoService.agregarAsiento(nuevoAsiento);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVuelo);
        } catch (Exception e) {
            // TODO: handle exception
            // preguntar profesor como podriamos devolver un error, no estoy pudiendo
            // por el tipo de dato que devuelve la funcion
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vuelo);
        }
    }
}
