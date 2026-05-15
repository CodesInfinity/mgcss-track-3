package com.mgcss.infrastructure; 

import java.util.List;
import java.util.Optional;

import com.mgcss.domain.solicitud.Solicitud;

public interface SolicitudRepository {
	Solicitud save(Solicitud solicitud); 
	Optional<Solicitud> findById(Long id); 
	List<Solicitud> findAll();
}
