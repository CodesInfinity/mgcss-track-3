package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;

class SiAsignarTecnicoSolicitudEnProcesoTest {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		Tecnico tecnico = Tecnico.crearTecnico("Juan", "Software");
		
		solicitud.asignarTecnico(tecnico);
		
		assertEquals(Estado.EN_PROCESO, solicitud.getEstado());
		
	}

}
