Crear solicitud
Objetivo: Verificar que el sistema permite el registro de una incidencia y asigna el estado inicial "ABIERTA" automáticamente.

Acción: POST

Endpoint: /api/solicitudes

Request:
{
	"descripcion": "Incidencia en el equipo de red"
}

Resultado esperado (200 OK):
{
	"id": 1,
	"descripcion": "Incidencia en el equipo de red",
	"estado": "ABIERTA"
}


------------------------------------------------------------------------------------------------------------------------

Consultar solicitud por ID
Objetivo: Verificar que el sistema recupera correctamente toda la información detallada de una solicitud existente.

Acción: GET

Endpoint: /api/solicitudes/1

Resultado esperado (200 OK):
{
	"id": 1,
	"descripcion": "Incidencia en el equipo de red",
	"estado": "EN_PROCESO",
	"tecnicoId": 2,
	"fechaCreacion": "2026-05-11T14:00:00Z",
	"historico": [...]
}

------------------------------------------------------------------------------------------------------------------------

Asignación de Técnico
Objetivo: Validar que al asignar un técnico a una solicitud abierta, el sistema cambia el estado a "EN_PROCESO" de forma automática.

Precondición: Solicitud en estado "ABIERTA".

Acción: PUT

Endpoint: /api/solicitudes/1/tecnicos

Request:
{
	"tecnicoId": 2
}

Verificación: Consultar la solicitud con GET /api/solicitudes/1

Resultado esperado:
{
	"estado": "EN_PROCESO",
	"tecnicoId": 2
}

------------------------------------------------------------------------------------------------------------------------

Cambiar estado de solicitud
Objetivo: Validar la posibilidad del cambio de estado de una solicitud siguiendo el flujo permitido.

Acción: PATCH

Endpoint: /api/solicitudes/estado

Request:
{
	"solicitudId": 1,
	"estado": "CERRADA"
}

Resultado esperado (200 OK): La solicitud se actualiza internamente. Al consultar de nuevo el histórico, debe aparecer la entrada "CERRADA".

------------------------------------------------------------------------------------------------------------------------

Reabrir solicitud
Objetivo: Verificar la funcionalidad de reapertura, la cual permite que una solicitud ya finalizada vuelva a ser atendida por el servicio técnico.

Precondición: La solicitud debe estar en estado "CERRADA".

Acción: PATCH

Endpoint: /api/solicitudes/1/reabrir

Resultado esperado (200 OK): El estado de la solicitud debe cambiar de "CERRADA" a "EN_PROCESO". La fecha de cierre (fechaCierre) debe resetearse a null según la lógica de la entidad Solicitud.

------------------------------------------------------------------------------------------------------------------------

Histórico de solicitudes
Objetivo: Evidenciar que cada cambio de estado queda registrado con su fecha correspondiente en el histórico de la solicitud.

Acción: GET

Endpoint: /api/solicitudes/1/historico

Resultado esperado (200 OK):
[
{
	"estado": "ABIERTA",
	"fecha": "2026-05-11T14:00:00Z"
},
{
	"estado": "EN_PROCESO",
	"fecha": "2026-05-11T14:05:00Z"
}
]