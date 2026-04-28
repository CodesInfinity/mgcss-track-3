package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;

class NoCerrarSolicitudSiNoEnProcesoTest {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		
		solicitud.setEstado(Estado.EN_PROCESO);
		
		solicitud.cerrar();
		
		assertEquals(Estado.CERRADA, solicitud.getEstado());
	}

}
