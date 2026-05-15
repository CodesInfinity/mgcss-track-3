package com.mgcss.service;

import com.mgcss.domain.tecnico.Tecnico;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.infrastructure.SolicitudRepository;

@Service
public class SolicitudService {
	private SolicitudRepository solicitudRepository;
	
	public SolicitudService(SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
	}
	
	public Solicitud crearSolicitud(String descripcion) {
		Solicitud sol = new Solicitud();
		sol.setDescripcion(descripcion);
		
		return solicitudRepository.save(sol);
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
	
	public Solicitud obtenerSolicitud(Long solicitudId){
		return solicitudRepository.findById(solicitudId).orElse(null);
		
	}
	
	public void reabrirSolicitud(Long solicitudId) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).orElse(null);
		
		if(solicitud != null && solicitud.getEstado() == Estado.CERRADA) {
			solicitud.reabrir();
		}
	}
	
	public List<Solicitud> listarSolicitudes() {
		return solicitudRepository.findAll();
	}

}
