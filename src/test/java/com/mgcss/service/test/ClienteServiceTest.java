package com.mgcss.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import com.mgcss.infrastructure.ClienteRepository;
import com.mgcss.service.ClienteService;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void crearClienteTest() {
        String nombre = "Empresa";
        String email = "contacto@gmail.com";
        TipoCliente tipo = TipoCliente.STANDARD;
        Cliente cliente = new Cliente(nombre, email, tipo);
        
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);


        Cliente resultado = clienteService.crearCliente(nombre, email, tipo);

        assertNotNull(resultado, "El cliente creado no debería ser nulo");
        assertEquals(nombre, resultado.getNombre());
        assertEquals(email, resultado.getEmail());
        assertEquals(tipo, resultado.getTipo());
        
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    
    @Test
	void obtenerClienteCuandoExisteTest() {
		Long id = 1L;
		Cliente clienteMock = new Cliente("Empresa", "contacto@gmail.com", TipoCliente.STANDARD);
		
		when(clienteRepository.find(id)).thenReturn(Optional.of(clienteMock));
		
		Cliente resultado = clienteService.obtenerCliente(id);
		
		assertNotNull(resultado);
		assertEquals(clienteMock, resultado);
		verify(clienteRepository, times(1)).find(id);
	}

	@Test
	void obtenerClienteCuandoNoExisteTest() {
		Long id = 100L;
		
		when(clienteRepository.find(id)).thenReturn(Optional.empty());
		
		Cliente resultado = clienteService.obtenerCliente(id);
		
		assertNull(resultado);
		verify(clienteRepository, times(1)).find(id);
	}
}
