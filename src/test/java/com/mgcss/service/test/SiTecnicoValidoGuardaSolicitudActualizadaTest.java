package com.mgcss.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.infrastructure.SolicitudRepository;
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
	
	@Test
	void obtenerSolicitudTest() {
		Solicitud solicitud = new Solicitud();
		
		when(solicitudRepository.findById(solicitud.getId())).thenReturn(Optional.of(solicitud));
		
		Solicitud resultado = solicitudService.obtenerSolicitud(solicitud.getId());
		
		assertNotNull(resultado);
		assertEquals(solicitud, resultado);
		verify(solicitudRepository, times(1)).findById(solicitud.getId());
	}
	
	@Test
	void reabrirSolicitudTest() {
		Long idCerrada = 1L;
		Long idAbierta = 2L;
		Long idNoExiste = 100L;
		
		Solicitud solicitudCerrada = new Solicitud();
		solicitudCerrada.setEstado(Estado.CERRADA);
		
		Solicitud solicitudAbierta = new Solicitud();
		
		when(solicitudRepository.findById(idCerrada)).thenReturn(Optional.of(solicitudCerrada));
		when(solicitudRepository.findById(idAbierta)).thenReturn(Optional.of(solicitudAbierta));
		when(solicitudRepository.findById(idNoExiste)).thenReturn(Optional.empty());
		
		solicitudService.reabrirSolicitud(idCerrada);
		solicitudService.reabrirSolicitud(idAbierta);
		assertDoesNotThrow(() -> solicitudService.reabrirSolicitud(idNoExiste));
		
		assertEquals(Estado.EN_PROCESO, solicitudCerrada.getEstado());
		assertEquals(Estado.ABIERTA, solicitudAbierta.getEstado());
		
		verify(solicitudRepository, times(1)).findById(idCerrada);
		verify(solicitudRepository, times(1)).findById(idAbierta);
		verify(solicitudRepository, times(1)).findById(idNoExiste);
	}
	
	@Test
	void listarSolicitudesTest() {
		List<Solicitud> solicitudesMock = List.of(new Solicitud(), new Solicitud());
		when(solicitudRepository.findAll()).thenReturn(solicitudesMock);
		
		List<Solicitud> resultado = solicitudService.listarSolicitudes();
		
		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals(solicitudesMock, resultado);
		verify(solicitudRepository, times(1)).findAll();
	}
	
	@Test
	void crearSolicitudGuardaYRetornaSolicitudTest() {
		String descripcion = "Pantalla rota";
		Solicitud solicitudGuardada = new Solicitud();
		solicitudGuardada.setId(1L);
		solicitudGuardada.setDescripcion(descripcion);
		
		when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitudGuardada);
		
		Solicitud resultado = solicitudService.crearSolicitud(descripcion);
		
		assertNotNull(resultado);
		assertEquals(descripcion, resultado.getDescripcion());
		verify(solicitudRepository, times(1)).save(any(Solicitud.class));
	}
	
	@Test
	void cambiarEstadoCuandoSolicitudExisteTest() {
		Long id = 1L;
		Solicitud solicitudMock = new Solicitud();
		solicitudMock.setId(id);
		solicitudMock.setEstado(Estado.ABIERTA); // Estado inicial
		
		when(solicitudRepository.findById(id)).thenReturn(Optional.of(solicitudMock));
		
		solicitudService.cambiarEstado(id, Estado.EN_PROCESO);
		
		assertEquals(Estado.EN_PROCESO, solicitudMock.getEstado());
		verify(solicitudRepository, times(1)).findById(id);
		verify(solicitudRepository, times(1)).save(solicitudMock);
	}
}
