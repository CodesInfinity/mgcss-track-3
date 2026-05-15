package com.mgcss.api.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.mgcss.api.dto.request.solicitud.SolicitudAsignarTecnicoRequestDto;
import com.mgcss.api.dto.request.solicitud.SolicitudCambiarEstadoRequestDto;
import com.mgcss.api.dto.request.solicitud.SolicitudCreateRequestDto;
import com.mgcss.api.dto.response.SolicitudResponseDto;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.service.SolicitudService;
import com.mgcss.service.TecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@Tag(name = "Solicitudes", description = "API para la gestión del ciclo de vida de las solicitudes (Creación, Asignación, Estados e Histórico)")
public class SolicitudController {
	private final SolicitudService solicitudService;
	private final TecnicoService tecnicoService;
	
	@Operation(
		summary = "Crear una nueva solicitud", 
		description = "Registra una nueva solicitud en el sistema con la descripción proporcionada. El estado inicial se establece automáticamente en ABIERTA."
	)
	@ApiResponse(responseCode = "200", description = "Solicitud creada exitosamente", content = @Content(schema = @Schema(implementation = SolicitudResponseDto.class)))
	@ApiResponse(responseCode = "400", description = "Error en los datos de entrada o no se pudo crear la solicitud", content = @Content)
	@PostMapping()
	public ResponseEntity<SolicitudResponseDto> crearSolicitud(@RequestBody SolicitudCreateRequestDto request){
		Solicitud solicitud = this.solicitudService.crearSolicitud(request.descripcion());
		
		if (solicitud != null) {
			return ResponseEntity.ok(SolicitudResponseDto.mapearASolicitudResponse(solicitud));
		}else {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	
	@Operation(
		summary = "Consultar solicitud por ID", 
		description = "Devuelve los detalles de una solicitud específica basándose en su identificador único."
	)
	@ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = SolicitudResponseDto.class)))
	@ApiResponse(responseCode = "400", description = "La solicitud no existe", content = @Content)
	@GetMapping("/{id}")
	public ResponseEntity<SolicitudResponseDto> consultarSolicitud(@PathVariable Long id){
		Solicitud solicitud = this.solicitudService.obtenerSolicitud(id);
		
		if(solicitud != null) {
			return ResponseEntity.ok(SolicitudResponseDto.mapearASolicitudResponse(solicitud));
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Operation(
		summary = "Asignar un técnico a la solicitud", 
		description = "Vincula un técnico existente a una solicitud. Esta acción cambiará internamente el estado de la solicitud a EN_PROCESO."
	)
	@ApiResponse(responseCode = "200", description = "Técnico asignado correctamente")
	@ApiResponse(responseCode = "400", description = "El técnico proporcionado no existe o no es válido")
	@PutMapping("/{id}/tecnicos")
	public ResponseEntity<Void> asignarTecnico(
			@PathVariable Long id,
			@RequestBody SolicitudAsignarTecnicoRequestDto request) {
		
		Tecnico tecnico = this.tecnicoService.obtenerTecnico(request.tecnicoId());
		
		if (tecnico == null) {
			return ResponseEntity.badRequest().build();
		}
		
		this.solicitudService.asignarTecnico(id, tecnico);
		
		return ResponseEntity.ok().build();
	}
	
	@Operation(
		summary = "Cambiar estado de la solicitud", 
		description = "Fuerza el cambio de estado de una solicitud (Ej. CERRADA, EN_PROCESO). Se pasa el ID de la solicitud y el nuevo estado en el cuerpo de la petición."
	)
	@ApiResponse(responseCode = "200", description = "Estado de la solicitud actualizado correctamente")
	@ApiResponse(responseCode = "404", description = "No se encontró la solicitud con el ID proporcionado")
	@PatchMapping("/estado")
	public ResponseEntity<Void> cambiarEstado(
			@RequestBody SolicitudCambiarEstadoRequestDto request) {
		
		
		Solicitud solicitud = this.solicitudService.obtenerSolicitud(request.solicitudId());
		if (solicitud == null) { 
			return ResponseEntity.notFound().build();
		}
		
		this.solicitudService.cambiarEstado(request.solicitudId(), request.estado());
		
		return ResponseEntity.ok().build();
	}
	
	@Operation(
		summary = "Reabrir solicitud", 
		description = "Permite pasar una solicitud que actualmente se encuentra en estado CERRADA nuevamente a EN_PROCESO."
	)
	@ApiResponse(responseCode = "200", description = "Operación completada (si la solicitud no estaba CERRADA, no se aplicarán cambios)")
	@PatchMapping("/{id}/reabrir")
	public ResponseEntity<Void> reabrirSolicitud(@PathVariable Long id) {
		this.solicitudService.reabrirSolicitud(id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(
		summary = "Consultar histórico de la solicitud", 
		description = "Devuelve una lista con todos los cambios de estado que ha sufrido la solicitud desde su creación, ordenados por fecha."
	)
	@ApiResponse(responseCode = "200", description = "Historial obtenido correctamente")
	@ApiResponse(responseCode = "404", description = "No se encontró la solicitud para consultar su historial")
	@GetMapping("/{id}/historico")
	public ResponseEntity<List<Solicitud.EstadoChange>> consultarHistoricoSolicitud(@PathVariable Long id) {
		Solicitud solicitud = this.solicitudService.obtenerSolicitud(id);
		
		if (solicitud != null) {
			return ResponseEntity.ok(solicitud.getHistorico());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
		@Operation(
			summary = "Consultar todas las solicitudes", 
			description = "Devuelve una lista con todos las solicitudes"
		)
		@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
		@ApiResponse(responseCode = "404", description = "No se encontraron solicitudes", content = @Content)
		@GetMapping("/listarSolicitudes")
		public ResponseEntity<List<Solicitud>> listarSolicitudes() {
			List<Solicitud> solicitudes = this.solicitudService.listarSolicitudes();
			
			if (solicitudes != null && !solicitudes.isEmpty()) {
		        return ResponseEntity.ok(solicitudes);
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}
}
