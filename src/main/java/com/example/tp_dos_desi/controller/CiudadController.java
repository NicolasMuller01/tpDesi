package com.example.tp_dos_desi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.tp_dos_desi.model.Ciudad;
import com.example.tp_dos_desi.service.CiudadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/ciudades/{id}")
    @ResponseBody
    public ResponseEntity<Optional<Ciudad>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ciudadService.buscarPorId(id));
    }

}
