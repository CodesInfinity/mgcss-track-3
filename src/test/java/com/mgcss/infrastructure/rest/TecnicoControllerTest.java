package com.mgcss.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.request.tecnico.TecnicoCreateRequestDto;
import com.mgcss.api.rest.TecnicoController;
import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.service.TecnicoService;

@WebMvcTest(TecnicoController.class)
class TecnicoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private TecnicoService tecnicoService;

	@Test
	void crearTecnicoSinEspecialidadRetorna200() throws Exception {
		TecnicoCreateRequestDto request = new TecnicoCreateRequestDto("Paco", "");
		Tecnico tecnicoMock = Tecnico.crearTecnico("Paco");
		tecnicoMock.setId(1L);

		// Como la especialidad viene vacía, el controlador debe llamar al método sin especialidad
		when(tecnicoService.crearTecnicoSinEspecialidad("Paco")).thenReturn(tecnicoMock);

		mockMvc.perform(post("/api/tecnicos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.nombre").value("Paco"));

		verify(tecnicoService, times(1)).crearTecnicoSinEspecialidad("Paco");
		verify(tecnicoService, times(0)).crearTecnico(any(), any());
	}

	@Test
	void crearTecnicoConEspecialidadRetorna200() throws Exception {
		TecnicoCreateRequestDto request = new TecnicoCreateRequestDto("Ana", "Redes");
		Tecnico tecnicoMock = Tecnico.crearTecnico("Ana", "Redes");
		tecnicoMock.setId(2L);

		when(tecnicoService.crearTecnico("Ana", "Redes")).thenReturn(tecnicoMock);

		mockMvc.perform(post("/api/tecnicos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2L))
				.andExpect(jsonPath("$.nombre").value("Ana"))
				.andExpect(jsonPath("$.especialidad").value("Redes"));

		verify(tecnicoService, times(1)).crearTecnico("Ana", "Redes");
		verify(tecnicoService, times(0)).crearTecnicoSinEspecialidad(any());
	}

	@Test
	void crearTecnicoConErrorRetorna400() throws Exception {
		TecnicoCreateRequestDto request = new TecnicoCreateRequestDto("Luis", null);

		// Simulamos que el servicio devuelve null por algún error de validación interna
		when(tecnicoService.crearTecnicoSinEspecialidad("Luis")).thenReturn(null);

		mockMvc.perform(post("/api/tecnicos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void obtenerTecnicoCuandoExisteRetorna200() throws Exception {
		Long id = 1L;
		Tecnico tecnicoMock = Tecnico.crearTecnico("Roberto", "Hardware");
		tecnicoMock.setId(id);

		when(tecnicoService.obtenerTecnico(id)).thenReturn(tecnicoMock);

		mockMvc.perform(get("/api/tecnicos/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.nombre").value("Roberto"))
				.andExpect(jsonPath("$.especialidad").value("Hardware"));
	}

	@Test
	void obtenerTecnicoCuandoNoExisteRetorna404() throws Exception {
		Long id = 99L;

		when(tecnicoService.obtenerTecnico(id)).thenReturn(null);

		mockMvc.perform(get("/api/tecnicos/{id}", id))
				.andExpect(status().isNotFound());
	}

	@Test
	void activarTecnicoRetorna200() throws Exception {
		Long id = 1L;

		mockMvc.perform(patch("/api/tecnicos/{id}/activar", id))
				.andExpect(status().isOk());

		verify(tecnicoService, times(1)).activarTecnico(id);
	}
}