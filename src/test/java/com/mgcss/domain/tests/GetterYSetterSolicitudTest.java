package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;

class GetterYSetterSolicitudTest {

	@Test
	void test() {
		Solicitud solicitud = new Solicitud();
        Cliente cliente = new Cliente("Ana", "ana@mail.com", null);
        Date fecha = new Date();
        
        solicitud.setCliente(cliente);
        solicitud.setDescripcion("Reparación de PC");
        solicitud.setFechaCreacion(fecha);
        solicitud.setEstado(Estado.ABIERTA);
        
        assertEquals(cliente, solicitud.getCliente());
        assertEquals("Reparación de PC", solicitud.getDescripcion());
        assertEquals(fecha, solicitud.getFechaCreacion());
        assertEquals(Estado.ABIERTA, solicitud.getEstado());
        assertNull(solicitud.getId());
	}

}
