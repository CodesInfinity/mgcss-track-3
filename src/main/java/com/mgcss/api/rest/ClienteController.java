package com.mgcss.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.dto.request.cliente.ClienteCreateRequestDto;
import com.mgcss.api.dto.response.ClienteResponseDto;
import com.mgcss.domain.cliente.Cliente;
import com.mgcss.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API para la gestión de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(
        summary = "Crear un nuevo cliente", 
        description = "Registra un nuevo cliente en el sistema con su nombre, email y tipo (STANDARD o PREMIUM)."
    )
    @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente", content = @Content(schema = @Schema(implementation = ClienteResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Error en los datos de entrada o no se pudo crear el cliente", content = @Content)
    @PostMapping()
    public ResponseEntity<ClienteResponseDto> crearCliente(@RequestBody ClienteCreateRequestDto request) {
        
        Cliente cliente = this.clienteService.crearCliente(
                request.nombre(), 
                request.email(), 
                request.tipo()
        );
        
        if (cliente != null) {
            return ResponseEntity.ok(ClienteResponseDto.mapearAClienteResponse(cliente));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}