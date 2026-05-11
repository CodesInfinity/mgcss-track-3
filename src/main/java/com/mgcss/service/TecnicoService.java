package com.mgcss.service;

import java.util.Optional;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.infrastructure.TecnicoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TecnicoService {
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico crearTecnicoSinEspecialidad(String nombre) {
		return tecnicoRepository.save(Tecnico.crearTecnico(nombre));
	}
	
	public Tecnico crearTecnico(String nombre, String especialidad) {
		return tecnicoRepository.save(Tecnico.crearTecnico(nombre, especialidad)); 
	}
	
	public void activarTecnico(Long id) {
		Optional<Tecnico> tecnicoOpt = tecnicoRepository.findById(id);
		
		if (tecnicoOpt.isPresent()) {
			Tecnico tecnico = tecnicoOpt.get();
			
			tecnico.activarTecnico();
			
			tecnicoRepository.save(tecnico);
		}
	}
	
	public Tecnico obtenerTecnico(Long tecnicoId) {
		return tecnicoRepository.findById(tecnicoId).orElse(null);
	}
}
