package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;

class NoAsignarTecnicosNoActivosASolicitudesTest {

	@Test
	void test() {
		Tecnico tecnico1 = Tecnico.crearTecnico("Juan");
		Tecnico tecnico2 = Tecnico.crearTecnico("Juan", "Software");
		Solicitud solicitud1 = new Solicitud();
		Solicitud solicitud2 = new Solicitud();
		
		solicitud1.setTecnico(tecnico1);
		solicitud2.setTecnico(tecnico2);
		
		assertNull(solicitud1.getTecnico(), "No debería haberse asignado el técnico y se ha hecho");
		assertNotNull(solicitud2.getTecnico(), "Debía haberse asignado un técnico y no se asignó");
		
	}

}
