package com.mgcss.infrastructure.persistence;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.mgcss.domain.Solicitud.Solicitud;
import com.mgcss.infrastructure.SolicitudRepository; // O infraestructure si no lo renombraste

@Repository
public class SolicitudRepositoryAdapter implements SolicitudRepository {

    private final JpaSolicitudRepository jpaRepository;

    public SolicitudRepositoryAdapter(JpaSolicitudRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return jpaRepository.findById(id).map(SolicitudEntity::toDomain);
    }
}