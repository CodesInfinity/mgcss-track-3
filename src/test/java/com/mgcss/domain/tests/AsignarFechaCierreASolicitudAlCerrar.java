package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;

class AsignarFechaCierreASolicitudAlCerrar {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		Tecnico tecnico = Tecnico.crearTecnico("Manuel", "Software");
		
		solicitud.asignarTecnico(tecnico);
		
		solicitud.cerrar();
		
		assertNotNull(solicitud.getFechaCierre(), "La fecha de cierre debería de haberse asignado");
		
	}

}
