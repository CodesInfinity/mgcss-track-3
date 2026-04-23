package com.mgcss.infrastructure;


import com.mgcss.domain.Cliente.Cliente;

public interface ClienteRepository {
	Cliente save(Cliente cliente);
}
