package com.mgcss.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "solicitudes")
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaCierre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntity tecnico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EstadoChangeEntity> historico = new ArrayList<>();

    // --- Mapeo Hexagonal ---
    public static SolicitudEntity fromDomain(Solicitud solicitud) {
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(solicitud.getId());
        entity.setEstado(solicitud.getEstado());
        entity.setDescripcion(solicitud.getDescripcion());
        entity.setFechaCreacion(solicitud.getFechaCreacion());
        entity.setFechaCierre(solicitud.getFechaCierre());
        
        if (solicitud.getTecnico() != null) {
            entity.setTecnico(TecnicoEntity.fromDomain(solicitud.getTecnico()));
        }
        
        if(solicitud.getCliente() != null) {
        	entity.setCliente(ClienteEntity.fromDomain(solicitud.getCliente()));
        }
        
        if (solicitud.getHistorico() != null) {
            entity.historico = solicitud.getHistorico().stream()
                .map(change -> new EstadoChangeEntity(change.estado(), change.fecha(), entity))
                .toList(); // Cambiado de .collect(Collectors.toList())
        }
        
        return entity;
    }

    public Solicitud toDomain() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(this.id);
        solicitud.setEstado(this.estado);
        solicitud.setDescripcion(this.descripcion);
        solicitud.setFechaCreacion(this.fechaCreacion);
        solicitud.setFechaCierre(this.fechaCierre);
        
        if (this.tecnico != null) {
            solicitud.setTecnico(this.tecnico.toDomain());
        }
        
        if(this.cliente != null) {
        	solicitud.setCliente(this.cliente.toDomain());
        }
        
        // El historial en el dominio es un record, se reconstruye si el dominio lo permite
        return solicitud;
    }
}