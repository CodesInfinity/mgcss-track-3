package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Cliente.Cliente;
import com.mgcss.domain.Solicitud.Solicitud;
import com.mgcss.domain.Enums.TipoCliente;

class AsignaClienteASolicitudTest {

	@Test
	void test() {
		Cliente cliente = new Cliente("Juan", "xxx@xxx.com", TipoCliente.STANDARD);
		Solicitud solicitud = new Solicitud();
		
		solicitud.setCliente(cliente);
		
		assertNotNull(solicitud.getCliente());
		
	}

}
