package com.mgcss.api.dto.response;

import java.util.Date;
import java.util.List;

import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.enums.Prioridad;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.domain.solicitud.Solicitud.EstadoChange;

import lombok.Builder;

@Builder
public record SolicitudResponseDto (
		Long id,
		Long clienteId,
		Long tecnicoId,
		String descripcion,
		Date fechaCreacion,
		Date fechaCierre,
		Estado estado,
		List<EstadoChange> historico,
		Prioridad prioridad
		){
	
	public static SolicitudResponseDto mapearASolicitudResponse(Solicitud solicitud) {
	    if (solicitud == null) {
	        return null;
	    }

	    return SolicitudResponseDto.builder()
	            .id(solicitud.getId())
	            .clienteId(solicitud.getCliente() != null ? solicitud.getCliente().getId() : null)
	            .tecnicoId(solicitud.getTecnico() != null ? solicitud.getTecnico().getId() : null)
	            .descripcion(solicitud.getDescripcion())
	            .fechaCreacion(solicitud.getFechaCreacion())
	            .fechaCierre(solicitud.getFechaCierre())
	            .estado(solicitud.getEstado())
	            .historico(solicitud.getHistorico())
	            .prioridad(solicitud.getPrioridad())
	            .build();
	}
}

