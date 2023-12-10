package com.example.tp_dos_desi.controller;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Avion;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.repository.VueloRepository;
import com.example.tp_dos_desi.service.AsientoService;
import com.example.tp_dos_desi.service.AvionService;
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

import java.util.List;

@Controller
public class AvionController {

    @Autowired
    private AvionService avionService;

    @GetMapping("/avion")
    public List<Avion> mostrarVuelos(Model model) {
        return avionService.buscarTodos();

    }

    @GetMapping("/avion/{id}")
    @ResponseBody
    public Avion mostrarAsientos(@PathVariable String id) {
        Avion avion = this.avionService.buscarPorId(id);

        return avion;
    }


    @PostMapping("/avion")
    public ResponseEntity<Avion> crearVuelo(@RequestBody Avion avion) {

        try {
            Avion nuevoAvion = this.avionService.guardar(avion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAvion);
        } catch (Exception e) {
            // TODO: handle exception
            // preguntar profesor como podriamos devolver un error, no estoy pudiendo
            // por el tipo de dato que devuelve la funcion
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(avion);
        }
    }
}
