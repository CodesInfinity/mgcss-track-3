package com.mgcss.infrastructure;


import java.util.Optional;

import com.mgcss.domain.cliente.Cliente;

public interface ClienteRepository {
	Cliente save(Cliente cliente);
	Optional<Cliente> find(Long id);
}
