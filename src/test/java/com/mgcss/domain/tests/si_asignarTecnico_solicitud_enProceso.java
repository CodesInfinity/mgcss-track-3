package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Enums.Estado;

class si_asignarTecnico_solicitud_enProceso {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
		Tecnico tecnico = Tecnico.crearTecnico("Juan", "Software");
		
		solicitud.asignarTecnico(tecnico);
		
		assertEquals(Estado.EN_PROCESO, solicitud.getEstado());
		
	}

}
