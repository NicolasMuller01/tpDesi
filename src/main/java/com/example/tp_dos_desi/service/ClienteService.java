package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Cliente;
import com.example.tp_dos_desi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
}
