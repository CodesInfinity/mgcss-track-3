package com.mgcss.api.dto.response;

import com.mgcss.domain.tecnico.Tecnico;

public record TecnicoResponseDto(
		Long id,
		String nombre,
		String especialidad,
		boolean activo
) {
	public static TecnicoResponseDto fromDomain(Tecnico tecnico) {
		return new TecnicoResponseDto(
				tecnico.getId(),
				tecnico.getNombre(),
				tecnico.getEspecialidad(),
				tecnico.isActivo()
		);
	}
}