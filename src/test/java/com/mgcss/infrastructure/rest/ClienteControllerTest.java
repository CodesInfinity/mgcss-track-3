package com.mgcss.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.request.cliente.ClienteCreateRequestDto;
import com.mgcss.api.rest.ClienteController;
import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import com.mgcss.service.ClienteService;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ClienteService clienteService;

    @Test
    void crearClienteConDatosValidosRetorna200() throws Exception {
        // Arrange: Preparamos el DTO de entrada y el objeto de dominio esperado
        ClienteCreateRequestDto request = new ClienteCreateRequestDto("Tech Solutions", "contacto@tech.com", TipoCliente.PREMIUM);
        Cliente clienteMock = new Cliente("Tech Solutions", "contacto@tech.com", TipoCliente.PREMIUM);
        clienteMock.setId(1L); // Simulamos que la BD le asignó un ID

        // Simulamos el comportamiento del servicio
        when(clienteService.crearCliente(any(String.class), any(String.class), any(TipoCliente.class)))
                .thenReturn(clienteMock);

        // Act & Assert: Ejecutamos la petición POST y verificamos respuesta y JSON
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Tech Solutions"))
                .andExpect(jsonPath("$.email").value("contacto@tech.com"))
                .andExpect(jsonPath("$.tipo").value("PREMIUM"));
        
        // Verificamos que el controlador llamó al servicio con los parámetros correctos
        verify(clienteService, times(1)).crearCliente("Tech Solutions", "contacto@tech.com", TipoCliente.PREMIUM);
    }

    @Test
    void crearClienteConFalloEnServicioRetorna400() throws Exception {
        // Arrange: Preparamos el DTO de entrada
        ClienteCreateRequestDto request = new ClienteCreateRequestDto("Tech Solutions", "contacto@tech.com", TipoCliente.PREMIUM);

        // Simulamos que el servicio falla y devuelve null
        when(clienteService.crearCliente(any(String.class), any(String.class), any(TipoCliente.class)))
                .thenReturn(null);

        // Act & Assert: Esperamos un 400 Bad Request
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}