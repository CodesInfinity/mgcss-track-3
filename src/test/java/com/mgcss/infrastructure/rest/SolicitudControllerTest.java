package com.mgcss.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.request.solicitud.SolicitudAsignarTecnicoRequestDto;
import com.mgcss.api.dto.request.solicitud.SolicitudCambiarEstadoRequestDto;
import com.mgcss.api.dto.request.solicitud.SolicitudCreateRequestDto;
import com.mgcss.api.rest.SolicitudController;
import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.service.SolicitudService;
import com.mgcss.service.TecnicoService;


@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private SolicitudService solicitudService;

	@MockitoBean
	private TecnicoService tecnicoService;

	@Test
	void crearSolicitudConDatosValidosRetorna200() throws Exception {
		SolicitudCreateRequestDto request = new SolicitudCreateRequestDto("Fallo en la red");
		Solicitud solicitud = new Solicitud();
		solicitud.setId(1L);
		solicitud.setDescripcion("Fallo en la red");
		solicitud.setEstado(Estado.ABIERTA);

		when(solicitudService.crearSolicitud(any(String.class))).thenReturn(solicitud);

		mockMvc.perform(post("/api/solicitudes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.descripcion").value("Fallo en la red"));
	}

	@Test
	void crearSolicitudConFalloEnServicioRetorna400() throws Exception {
		SolicitudCreateRequestDto request = new SolicitudCreateRequestDto("Fallo en la red");

		when(solicitudService.crearSolicitud(any(String.class))).thenReturn(null);

		mockMvc.perform(post("/api/solicitudes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void consultarSolicitudCuandoExisteRetorna200() throws Exception {
		Long id = 1L;
		Solicitud solicitud = new Solicitud();
		solicitud.setId(id);
		solicitud.setDescripcion("Mantenimiento");
		solicitud.setEstado(Estado.EN_PROCESO);

		when(solicitudService.obtenerSolicitud(id)).thenReturn(solicitud);

		mockMvc.perform(get("/api/solicitudes/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.descripcion").value("Mantenimiento"));
	}

	@Test
	void consultarSolicitudCuandoNoExisteRetorna400() throws Exception {
		Long id = 100L;
		when(solicitudService.obtenerSolicitud(id)).thenReturn(null);

		mockMvc.perform(get("/api/solicitudes/{id}", id))
				.andExpect(status().isBadRequest());
	}

	@Test
	void asignarTecnicoConTecnicoValidoRetorna200() throws Exception {
		Long idSolicitud = 1L;
		SolicitudAsignarTecnicoRequestDto request = new SolicitudAsignarTecnicoRequestDto(2L);
		Tecnico tecnico = Tecnico.crearTecnico("Paco", "Hardware");

		when(tecnicoService.obtenerTecnico(request.tecnicoId())).thenReturn(tecnico);

		mockMvc.perform(put("/api/solicitudes/{id}/tecnicos", idSolicitud)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());

		verify(solicitudService, times(1)).asignarTecnico(idSolicitud, tecnico);
	}

	@Test
	void asignarTecnicoConTecnicoInvalidoRetorna400() throws Exception {
		Long idSolicitud = 1L;
		SolicitudAsignarTecnicoRequestDto request = new SolicitudAsignarTecnicoRequestDto(99L);

		when(tecnicoService.obtenerTecnico(request.tecnicoId())).thenReturn(null);

		mockMvc.perform(put("/api/solicitudes/{id}/tecnicos", idSolicitud)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void cambiarEstadoCuandoSolicitudExisteRetorna200() throws Exception {
		Long idSolicitud = 1L;
		SolicitudCambiarEstadoRequestDto request = new SolicitudCambiarEstadoRequestDto(idSolicitud, Estado.CERRADA);
		Solicitud solicitud = new Solicitud();

		when(solicitudService.obtenerSolicitud(request.solicitudId())).thenReturn(solicitud);

		mockMvc.perform(patch("/api/solicitudes/estado")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());

		verify(solicitudService, times(1)).cambiarEstado(request.solicitudId(), request.estado());
	}

	@Test
	void cambiarEstadoCuandoSolicitudNoExisteRetorna404() throws Exception {
		SolicitudCambiarEstadoRequestDto request = new SolicitudCambiarEstadoRequestDto(99L, Estado.CERRADA);

		when(solicitudService.obtenerSolicitud(request.solicitudId())).thenReturn(null);

		mockMvc.perform(patch("/api/solicitudes/estado")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isNotFound());
	}

	@Test
	void reabrirSolicitudRetorna200() throws Exception {
		Long id = 1L;

		mockMvc.perform(patch("/api/solicitudes/{id}/reabrir", id))
				.andExpect(status().isOk());

		verify(solicitudService, times(1)).reabrirSolicitud(id);
	}

	@Test
	void consultarHistoricoCuandoExisteRetornaLista() throws Exception {
		Long id = 1L;
		Solicitud solicitud = new Solicitud();
		List<Solicitud.EstadoChange> historico = solicitud.getHistorico();

		when(solicitudService.obtenerSolicitud(id)).thenReturn(solicitud);

		mockMvc.perform(get("/api/solicitudes/{id}/historico", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(historico.size()))
				.andExpect(jsonPath("$[0].estado").value("ABIERTA"));
	}

	@Test
	void consultarHistoricoCuandoNoExisteRetorna404() throws Exception {
		Long id = 99L;

		when(solicitudService.obtenerSolicitud(id)).thenReturn(null);

		mockMvc.perform(get("/api/solicitudes/{id}/historico", id))
				.andExpect(status().isNotFound());
	}
}