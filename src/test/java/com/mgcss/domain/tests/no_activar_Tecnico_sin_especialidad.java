package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Tecnico;

class no_activar_Tecnico_sin_especialidad {

	@Test
	void test() {
		Tecnico tecnico = Tecnico.crearTecnico("Manuel");
		
		tecnico.activarTecnico();
		
		assertEquals(false, tecnico.isActivo());
	}

}
