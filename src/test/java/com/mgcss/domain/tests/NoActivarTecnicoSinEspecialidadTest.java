package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Tecnico;

class NoActivarTecnicoSinEspecialidadTest {

	@Test
	void test() {
		Tecnico tecnico = Tecnico.crearTecnico("Manuel");
		
		tecnico.activarTecnico();
		
		assertEquals(false, tecnico.isActivo());
	}

}
