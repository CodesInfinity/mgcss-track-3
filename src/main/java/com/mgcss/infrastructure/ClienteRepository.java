package com.mgcss.infrastructure;


import com.mgcss.domain.cliente.Cliente;

public interface ClienteRepository {
	Cliente save(Cliente cliente);
}
