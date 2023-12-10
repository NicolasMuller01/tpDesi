package com.example.tp_dos_desi.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tp_dos_desi.dtos.VueloDTO;
import com.example.tp_dos_desi.model.Asiento;
import com.example.tp_dos_desi.model.Cliente;
import com.example.tp_dos_desi.model.Vuelo;
import com.example.tp_dos_desi.service.AsientoService;
import com.example.tp_dos_desi.service.ClienteService;
import com.example.tp_dos_desi.service.VueloService;

@Controller
public class TemplateController {

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private VueloService vueloService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/asientos/{numeroVuelo}")
    public String mostrarAsientos(@PathVariable String numeroVuelo, Model model) {

        Vuelo vuelo = vueloService.buscarPorNumeroVuelo(numeroVuelo);

        model.addAttribute("vuelo", vuelo);
        model.addAttribute("asientosDisponibles", vuelo.getAsientos());
        return "asientos";
    }

    @GetMapping("/comprar-asiento/{idAsiento}")
    public String comprarAsiento(@RequestParam("dni") String dni, @PathVariable Long idAsiento, Model model) {
        Asiento asiento = this.asientoService.buscarPorId(idAsiento);
        Cliente cliente = this.clienteService.buscarPorDni(dni);
        Optional<Vuelo> vuelo = this.vueloService.buscarPorId(asiento.getVuelo().getId());

        if (asiento.isDisponible()) {

            asiento.setDisponible(false);
            asiento.setCliente(cliente);
            this.asientoService.guardar(asiento);

            model.addAttribute("cliente", cliente);
            model.addAttribute("asiento", asiento);
            model.addAttribute("origen", vuelo.get().getCiudadOrigen().getNombre());
            model.addAttribute("destino", vuelo.get().getCiudadDestino().getNombre());
            model.addAttribute("vuelo", vuelo.get());

            return "asientoComprado";
        } else {
            model.addAttribute("error", "El asiento no está disponible.");
            model.addAttribute("vuelo", vuelo.get());
            model.addAttribute("cliente", cliente);
            return "asientos";
        }
    }

    @GetMapping("/clientes")
    public String index() {
        return "buscarCliente";
    }

    @PostMapping("/elegir-vuelo")
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
            return "clienteEleccionVuelo";
        } else {
            model.addAttribute("error", "El cliente no está registrado.");
            return "clienteEleccionVuelo";
        }
    }

    @GetMapping("/resultado")
    public String mostrarResultado(@RequestParam("dni") String dni, @RequestParam("fecha") String fecha,
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino, @RequestParam("vuelo") String vuelo,
            Model model) {
        List<VueloDTO> objetosFiltrados = VueloDTO.filtrarObjetos(fecha, origen, destino, vuelo,
                this.vueloService.buscarTodos());
        // Otros procesamientos y filtrados

        model.addAttribute("objetosFiltrados", objetosFiltrados);
        model.addAttribute("dni", dni);
        // Otros atributos del modelo

        return "vuelos";
    }

    @GetMapping("/vuelo/{id}")
    public String mostrarVuelos(@PathVariable Long id, @RequestParam("dni") String dni, Model model) {
        Optional<Vuelo> vuelo = vueloService.buscarPorId(id);

        model.addAttribute("vuelo", vuelo.get());
        model.addAttribute("origen", vuelo.get().getCiudadOrigen().getNombre());
        model.addAttribute("destino", vuelo.get().getCiudadDestino().getNombre());

        List<Asiento> asientos = vuelo.get().getAsientos();
        List<Asiento> asientosFiltrados = new ArrayList<>();

        String fecha = vuelo.get().getFecha().toString();

        for (Asiento a : asientos) {
            if (a.isDisponible()) {
                asientosFiltrados.add(a);
            }
        }

        model.addAttribute("asientos", asientosFiltrados);
        model.addAttribute("dni", dni);
        model.addAttribute("fecha", fecha);

        String tipoVuelo = new String();
        if (vuelo.get().getCiudadOrigen().getPais().equals(vuelo.get().getCiudadDestino().getPais())) {
            tipoVuelo = "Nacional";
        } else {
            tipoVuelo = "Internacional";
        }

        model.addAttribute("tipoVuelo", tipoVuelo);

        return "vuelo";
    }

}
