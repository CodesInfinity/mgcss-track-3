package com.mgcss.service;

import org.springframework.stereotype.Service;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import com.mgcss.infrastructure.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;
	
	public Cliente crearCliente(String nombre, String email, TipoCliente tipo) {		
		return clienteRepository.save(new Cliente(nombre, email, tipo));
	}
	
	public Cliente obtenerCliente(Long id) {
		return clienteRepository.find(id).orElse(null);
	}
}
