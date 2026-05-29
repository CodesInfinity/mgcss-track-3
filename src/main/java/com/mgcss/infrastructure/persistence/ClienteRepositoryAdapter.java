package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.infrastructure.ClienteRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final JpaClienteRepository jpaRepository;

    public ClienteRepositoryAdapter(JpaClienteRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = ClienteEntity.fromDomain(cliente);
        return jpaRepository.save(entity).toDomain();
    }

	@Override
	public Optional<Cliente> find(Long id) {
		Optional<ClienteEntity> entity = jpaRepository.findById(id);
		return entity.map(ClienteEntity::toDomain);
	}
}