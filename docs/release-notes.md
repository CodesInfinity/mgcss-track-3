# Release Notes

## Versión 1.1.0

### Justificación del versionado
Se ha decidido aplicar un incremento **MINOR** (pasando de la 1.0.0 a la 1.1.0) siguiendo el estándar de Semantic Versioning. 

**Motivos:**
- Se ha introducido una nueva funcionalidad (Feature): El registro automático del histórico de cambios de estado en las solicitudes (`EstadoChangeEntity`).
- Se han expuesto nuevos datos en los DTOs de respuesta de la API (`SolicitudResponseDto` ahora devuelve la lista del histórico).
- Estos cambios son retrocompatibles y no rompen el contrato de los endpoints existentes.
- Se han aplicado diversas refactorizaciones de código (corrección de nomenclatura de paquetes, optimización de lambdas y constructores vacíos) que mejoran la mantenibilidad pero no alteran el comportamiento externo del sistema.