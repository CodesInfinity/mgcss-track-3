package com.mgcss.api.dto.request.cliente;

import com.mgcss.domain.enums.TipoCliente;

public record ClienteCreateRequestDto(
        String nombre,
        String email,
        TipoCliente tipo
) {}