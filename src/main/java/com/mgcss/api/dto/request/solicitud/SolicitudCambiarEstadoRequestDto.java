package com.mgcss.api.dto.request.solicitud;

import com.mgcss.domain.enums.Estado;

public record SolicitudCambiarEstadoRequestDto (Long solicitudId, Estado estado){

}
