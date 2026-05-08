package com.mgcss.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
}