package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud.Solicitud;
import com.mgcss.domain.Enums.Estado;
import com.mgcss.domain.Tecnico.Tecnico;

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
