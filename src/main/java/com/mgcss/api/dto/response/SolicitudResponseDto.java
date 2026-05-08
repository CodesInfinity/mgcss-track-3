package com.mgcss.api.dto.response;

import java.util.Date;

import com.mgcss.domain.enums.Estado;

public record SolicitudResponseDto (
		long id,
		long clienteId,
		long tecnicoId,
		String descripcion,
		Date fechaCreacion,
		Date fechaCierre,
		Estado estado
		){}
