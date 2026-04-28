package com.mgcss.service;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import com.mgcss.infrastructure.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClienteService {
	private ClienteRepository clienteRepository;
	
	public Cliente crearCliente(String nombre, String email, TipoCliente tipo) {		
		return clienteRepository.save(new Cliente(nombre, email, tipo));
	}
}
