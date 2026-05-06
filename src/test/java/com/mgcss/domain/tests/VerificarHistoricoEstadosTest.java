package com.mgcss.domain.tests;

import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.domain.enums.Estado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VerificarHistoricoEstadosTest {

	@Test
	void cambiarEstado_RegistraEnHistorico() {
	    Solicitud solicitud = new Solicitud(); // +1 (ABIERTA)
	    
	    solicitud.setEstado(Estado.EN_PROCESO); // +1
	    solicitud.cerrar();                     // +1
	    solicitud.reabrir();                    // +1
	    
	    assertEquals(4, solicitud.getHistorico().size());
	    assertEquals(Estado.ABIERTA, solicitud.getHistorico().get(0).estado());
	    assertEquals(Estado.EN_PROCESO, solicitud.getHistorico().get(3).estado());
	}
}