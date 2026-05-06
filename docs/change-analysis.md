¿Qué métodos del dominio se ven afectados?

Se debe crear un nuevo método reabrir() en la entidad Solicitud.  Los métodos que cambian de estado (como cerrar() o asignar un técnico) deberán modificarse internamente para registrar el cambio en el nuevo histórico.

¿Qué reglas actuales cambian?

Hasta ahora, el estado CERRADA era terminal. La nueva regla permite que una solicitud en estado CERRADA transicione de vuelta a EN_PROCESO.

¿Qué tests deberían romperse?

Es posible que no se rompan por el flujo de "reabrir" (ya que es nuevo), pero la inclusión del histórico podría romper tests de instanciación si modificamos el constructor de Solicitud, o tests de persistencia (como JpaSolicitudRepositoryTest) si la base de datos ahora exige un formato diferente.  

¿Qué parte del modelo debe extenderse?

La entidad Solicitud necesita una nueva estructura para el histórico. Puede ser una lista interna de un Value Object (ej. EstadoChange) que guarde el estado y la fecha del cambio.  

¿Qué impacto tiene en persistencia?

La entidad de base de datos SolicitudEntity deberá reflejar este nuevo histórico. Dependiendo del enfoque, requerirá una colección anexa (como @ElementCollection o una relación @OneToMany) y su mapeo correspondiente en SolicitudRepositoryAdapter.

---

Conclusión de la implementación.

    - Refactorización: Se ha centralizado el registro del historial en el método `setEstado(Estado)`, simplificando los métodos de negocio (`cerrar`, `reabrir`, `asignarTecnico`) y garantizando la trazabilidad total.

    - Persistencia: Se confirma el uso de una relación `@OneToMany` con `EstadoChangeEntity` para permitir un crecimiento independiente del histórico respecto a la entidad principal.