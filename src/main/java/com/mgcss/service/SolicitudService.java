package com.mgcss.service;

import com.mgcss.domain.Enums.Estado;
import com.mgcss.domain.Solicitud.Solicitud;
import com.mgcss.domain.Tecnico.Tecnico;
import com.mgcss.infrastructure.SolicitudRepository;

import jakarta.persistence.EntityNotFoundException;

public class SolicitudService {
	private SolicitudRepository solicitudRepository;
	
	public SolicitudService(SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
	}
	
	public Solicitud crearSolicitud() {
		return new Solicitud();
	}
	
	public void asignarTecnico(Long solicitudId, Tecnico tecnico) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).orElseThrow(() -> new IllegalArgumentException());
		
		if(solicitud != null && tecnico != null) {
			solicitud.asignarTecnico(tecnico);
		}
		
		solicitudRepository.save(solicitud);
	}
	
	public void cambiarEstado(Long solicitudId, Estado estado) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).orElse(null);
		
		if(solicitud != null) {
			solicitud.setEstado(estado);;
		}
		
		solicitudRepository.save(solicitud);
	}
	
}
