package com.mgcss.service;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.infrastructure.SolicitudRepository;

public class SolicitudService {
	private SolicitudRepository solicitudRepository;
	
	public SolicitudService(SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
	}
	
	public Solicitud crearSolicitud() {
		return new Solicitud();
	}
	
	public void asignarTecnico(Long solicitudId, Tecnico tecnico) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).orElseThrow(IllegalArgumentException::new);
		
		if(solicitud != null && tecnico != null) {
			solicitud.asignarTecnico(tecnico);
		}
		
		solicitudRepository.save(solicitud);
	}
	
	public void cambiarEstado(Long solicitudId, Estado estado) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).orElse(null);
		
		if(solicitud != null) {
			solicitud.setEstado(estado);
		}
		
		solicitudRepository.save(solicitud);
	}
	
}
