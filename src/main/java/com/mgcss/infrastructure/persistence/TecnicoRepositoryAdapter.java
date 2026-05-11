package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.infrastructure.TecnicoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TecnicoRepositoryAdapter implements TecnicoRepository {

    private final JpaTecnicoRepository jpaRepository;

    public TecnicoRepositoryAdapter(JpaTecnicoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Tecnico save(Tecnico tecnico) {
        TecnicoEntity entity = TecnicoEntity.fromDomain(tecnico);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Tecnico> findById(Long id) {
        return jpaRepository.findById(id).map(TecnicoEntity::toDomain);
    }
}