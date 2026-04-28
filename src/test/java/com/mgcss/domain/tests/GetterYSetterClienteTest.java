package com.mgcss.domain.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;


class GetterYSetterClienteTest {

	@Test
	void test() {
		String nombre = "Juan Perez";
        String email = "juan@example.com";
        TipoCliente tipo = TipoCliente.STANDARD;

        Cliente cliente = new Cliente(nombre, email, tipo);
        
        assertEquals(nombre, cliente.getNombre(), "El nombre no coincide");
        assertEquals(email, cliente.getEmail(), "El email no coincide");
        assertEquals(tipo, cliente.getTipo(), "El tipo de cliente no coincide");
        
        assertNotNull(cliente.getId(), "El ID debería haberse generado automáticamente");
	}

}
