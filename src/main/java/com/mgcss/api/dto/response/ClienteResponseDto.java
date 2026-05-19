package com.mgcss.api.dto.response;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import lombok.Builder;

@Builder
public record ClienteResponseDto(
        Long id,
        String nombre,
        String email,
        TipoCliente tipo
) {
    public static ClienteResponseDto mapearAClienteResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return ClienteResponseDto.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .tipo(cliente.getTipo())
                .build();
    }
}