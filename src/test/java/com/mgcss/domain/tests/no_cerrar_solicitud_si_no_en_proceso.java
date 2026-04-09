package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Enums.Estado;

import lombok.RequiredArgsConstructor;

class no_cerrar_solicitud_si_no_en_proceso {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		
		solicitud.setEstado(Estado.EN_PROCESO);
		
		solicitud.cerrar();
		
		assertEquals(Estado.CERRADA, solicitud.getEstado());
	}

}
