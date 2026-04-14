package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Tecnico.Tecnico;

class GetterYSetterTecnicoTest {

	@Test
	void test() {
		Tecnico tecnico = Tecnico.crearTecnico("Juan", "Fontanero");
		
		tecnico.setNombre("Pepe");
	    assertEquals("Pepe", tecnico.getNombre());
	    
	    tecnico.setEspecialidad("Electricista");
	    assertEquals("Electricista", tecnico.getEspecialidad());
	    
	    tecnico.setActivo(true);
	    assertTrue(tecnico.isActivo());
	    assertTrue(tecnico.getActivo());
	    
	}

}
