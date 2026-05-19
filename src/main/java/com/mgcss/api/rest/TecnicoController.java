package com.mgcss.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.dto.request.tecnico.TecnicoCreateRequestDto;
import com.mgcss.api.dto.response.TecnicoResponseDto;
import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.service.TecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tecnicos")
@RequiredArgsConstructor
@Tag(name = "Técnicos", description = "API para la gestión del personal técnico")
public class TecnicoController {

	private final TecnicoService tecnicoService;

	@Operation(
		summary = "Crear un nuevo técnico", 
		description = "Registra un nuevo técnico en el sistema. Si no se proporciona especialidad, se creará sin ella."
	)
	@ApiResponse(responseCode = "200", description = "Técnico creado exitosamente")
	@ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o hubo un error al crear", content = @Content)
	@PostMapping
	public ResponseEntity<TecnicoResponseDto> crearTecnico(@RequestBody TecnicoCreateRequestDto request) {
		Tecnico tecnico;
		
		if (request.especialidad() == null || request.especialidad().isBlank()) {
			tecnico = tecnicoService.crearTecnicoSinEspecialidad(request.nombre());
		} else {
			tecnico = tecnicoService.crearTecnico(request.nombre(), request.especialidad());
		}

		if (tecnico != null) {
			return ResponseEntity.ok(TecnicoResponseDto.fromDomain(tecnico));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@Operation(
		summary = "Consultar técnico por ID", 
		description = "Devuelve los detalles de un técnico basándose en su identificador único."
	)
	@ApiResponse(responseCode = "200", description = "Técnico encontrado correctamente")
	@ApiResponse(responseCode = "404", description = "No se encontró ningún técnico con el ID proporcionado", content = @Content)
	@GetMapping("/{id}")
	public ResponseEntity<TecnicoResponseDto> obtenerTecnico(@PathVariable Long id) {
		Tecnico tecnico = tecnicoService.obtenerTecnico(id);

		if (tecnico != null) {
			return ResponseEntity.ok(TecnicoResponseDto.fromDomain(tecnico));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(
		summary = "Activar técnico", 
		description = "Marca el estado del técnico como activado."
	)
	@ApiResponse(responseCode = "200", description = "Técnico activado correctamente")
	@PatchMapping("/{id}/activar")
	public ResponseEntity<Void> activarTecnico(@PathVariable Long id) {
		tecnicoService.activarTecnico(id);
		return ResponseEntity.ok().build();
	}
}