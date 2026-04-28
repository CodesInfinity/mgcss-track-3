package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.domain.solicitud.Solicitud;

class AsignarFechaCierreASolicitudAlCerrarTest {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		Tecnico tecnico = Tecnico.crearTecnico("Manuel", "Software");
		
		solicitud.asignarTecnico(tecnico);
		
		solicitud.cerrar();
		
		assertNotNull(solicitud.getFechaCierre(), "La fecha de cierre debería de haberse asignado");
		
	}

}
