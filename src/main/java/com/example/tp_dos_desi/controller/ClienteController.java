package com.example.tp_dos_desi.controller;

import com.example.tp_dos_desi.model.Cliente;
import com.example.tp_dos_desi.repository.ClienteRepository;
import com.example.tp_dos_desi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/buscarCliente")
    public String buscarCliente(@RequestParam("dni") String dni, Model model) {
        Cliente cliente = clienteService.buscarPorDni(dni);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "mostrarCliente";
        } else {
            model.addAttribute("error", "El cliente no está registrado.");
            return "index";
        }
    }
}
