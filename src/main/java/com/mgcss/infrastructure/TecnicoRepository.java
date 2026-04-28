package com.mgcss.infrastructure;

import java.util.Optional;

import com.mgcss.domain.tecnico.Tecnico;

public interface TecnicoRepository {
	Tecnico save(Tecnico tecnico);
	Optional<Tecnico> findById(Long id);
}
