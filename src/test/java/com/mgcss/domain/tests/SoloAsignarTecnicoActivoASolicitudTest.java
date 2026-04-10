package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;

class SoloAsignarTecnicoActivoASolicitudTest {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		Tecnico tec1 = Tecnico.crearTecnico("Manuel", "Software");
		Tecnico tec2 = Tecnico.crearTecnico("Guille", "Software");
		
		solicitud.asignarTecnico(tec1);
		solicitud.asignarTecnico(tec2);
		
		assertEquals(true, solicitud.getTecnico().getActivo());
	}

}
