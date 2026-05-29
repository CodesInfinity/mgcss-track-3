package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import com.mgcss.domain.solicitud.Solicitud;

class AsignaClienteASolicitudTest {

	@Test
	void test() {
		Cliente cliente = new Cliente("Juan", "xxx@xxx.com", TipoCliente.STANDARD);
		Solicitud solicitud = new Solicitud();
		
		solicitud.setCliente(cliente);
		
		assertNotNull(solicitud.getCliente());
		
	}
	
	@Test
	void testAsignarCliente() {
		Cliente cliente = new Cliente("Juan", "xxx@xxx.com", TipoCliente.STANDARD);
		Solicitud solicitud = new Solicitud();
		
		solicitud.asignarCliente(cliente);
		
		assertNotNull(solicitud.getCliente());
		
	}

}
