package com.example.tp_dos_desi.controller;

import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Cliente;
import com.example.tp_dos_desi.model.Ticket;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.service.AsientoService;
import com.example.tp_dos_desi.service.ClienteService;
import com.example.tp_dos_desi.service.TicketService;
import com.example.tp_dos_desi.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TicketController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VueloService vueloService;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticket")
    public String index(Model model) {
        List<Vuelo> vuelos = vueloService.buscarTodos();
        model.addAttribute("vuelos", vuelos);
        return "index";
    }

    @PostMapping("/buscarTicket")
    public String buscarCliente(@RequestParam("dni") String dni, Model model) {
        Cliente cliente = clienteService.buscarPorDni(dni);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "datosCliente";
        } else {
            model.addAttribute("error", "El cliente no está registrado.");
            return "index";
        }
    }

    @GetMapping("/asientos-ticket/{numeroVuelo}")
    public String mostrarAsientos(@PathVariable String numeroVuelo, Model model) {
        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);

        model.addAttribute("vuelo", vuelo);

        return "asientos";
    }

    @GetMapping("/precio/{numeroVuelo}/{numeroAsiento}")
    @ResponseBody
    public double mostrarPrecio(@PathVariable String numeroVuelo, @PathVariable String numeroAsiento) {
        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);
        Asiento asiento = asientoService.buscarPorNumeroAsiento(numeroAsiento);
        return vuelo.getPrecio() + asiento.getPrecio();
    }

    @PostMapping("/emitirTicket")
    public String emitirTicket(@RequestParam("dni") String dni, @RequestParam("numeroVuelo") String numeroVuelo, @RequestParam("numeroAsiento") String numeroAsiento, Model model) {
        Cliente cliente = clienteService.buscarPorDni(dni);
        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);
        Asiento asiento = asientoService.buscarPorNumeroAsiento(numeroAsiento);
        if (cliente != null && vuelo != null && asiento != null && asiento.isDisponible()) {
            Ticket ticket = new Ticket();
            ticket.setCliente(cliente);
            ticket.setVuelo(vuelo);
            ticket.setAsiento(asiento);
            ticket.setPrecioTotal(vuelo.getPrecio() + asiento.getPrecio());
            ticketService.guardar(ticket);
            asiento.setDisponible(false);
            asientoService.guardar(asiento);
            model.addAttribute("ticket", ticket);
            return "ticket";
        } else {
            model.addAttribute("error", "No se pudo emitir el ticket.");
            return "index";
        }
    }
}