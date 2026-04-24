package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;

class NoAsignarTecnicoASolicitudCerradaTest {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		Tecnico tecnico = Tecnico.crearTecnico("Juan", "Software");
		
		solicitud.setEstado(Estado.CERRADA);
		
		solicitud.asignarTecnico(tecnico);
		
		assertEquals(null, solicitud.getTecnico());
	}

}
