package com.mgcss.infrastructure.persistence;

import jakarta.persistence.*;
import java.util.Date;
import com.mgcss.domain.Enums.Estado;
import com.mgcss.domain.Solicitud.Solicitud;

@Entity
@Table(name = "solicitudes")
public class SolicitudEntity {

    @Id
    private Long id; // Usamos el ID que ya genera tu dominio

    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaCierre;

    public SolicitudEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Date getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(Date fechaCierre) { this.fechaCierre = fechaCierre; }

    // --- Mapeo Hexagonal ---
    public static SolicitudEntity fromDomain(Solicitud solicitud) {
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(solicitud.getId());
        entity.setEstado(solicitud.getEstado());
        entity.setDescripcion(solicitud.getDescripcion());
        entity.setFechaCreacion(solicitud.getFechaCreacion());
        entity.setFechaCierre(solicitud.getFechaCierre());
        return entity;
    }

    public Solicitud toDomain() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(this.id);
        solicitud.setEstado(this.estado);
        solicitud.setDescripcion(this.descripcion);
        solicitud.setFechaCreacion(this.fechaCreacion);
        solicitud.setFechaCierre(this.fechaCierre);
        return solicitud;
    }
}