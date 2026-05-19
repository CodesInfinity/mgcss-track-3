package com.mgcss.api.dto.request.tecnico;

import io.swagger.v3.oas.annotations.media.Schema;

public record TecnicoCreateRequestDto(
		@Schema(description = "Nombre del técnico", example = "Manuel")
		String nombre,
		
		@Schema(description = "Especialidad del técnico (opcional)", example = "Hardware")
		String especialidad
) {}