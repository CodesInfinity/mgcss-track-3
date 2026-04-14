package com.mgcss.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.Enums.Estado;
import com.mgcss.domain.Solicitud.Solicitud;
import com.mgcss.domain.Tecnico.Tecnico;
import com.mgcss.infraestructure.SolicitudRepository;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
class SiTecnicoValidoGuardaSolicitudActualizadaTest {

	@Mock
	private SolicitudRepository solicitudRepository;
	
	@InjectMocks
	private SolicitudService solicitudService;
	
	@Test
	void test() {
		Tecnico tecnico = Tecnico.crearTecnico("Manuel", "Software");
		Solicitud solicitud = new Solicitud();
		
		when(solicitudRepository.findById(solicitud.getId())).thenReturn(Optional.of(solicitud));
		
		solicitudService.asignarTecnico(solicitud.getId(), tecnico);
		
		
		verify(solicitudRepository, times(1)).save(any());
		assertEquals(Estado.EN_PROCESO, solicitud.getEstado());
	}
	
	@Test
	void SiSolicitudNoExixtesLanzaExcepcionTest(){
		Tecnico tecnico = Tecnico.crearTecnico("Manuel", "Software");
		
		when(solicitudRepository.findById(100L)).thenReturn(Optional.empty());
		
		assertThrows(IllegalArgumentException.class, () -> solicitudService.asignarTecnico(100L, tecnico));
		
		verify(solicitudRepository, never()).save(any());
	}

}
