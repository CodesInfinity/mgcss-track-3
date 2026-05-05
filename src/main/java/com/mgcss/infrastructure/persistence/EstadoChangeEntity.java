package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.enums.Estado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "solicitud_historico")
@Getter
@Setter
@NoArgsConstructor
public class EstadoChangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id")
    private SolicitudEntity solicitud;

    // Constructor manual para asegurar la compatibilidad con el stream
    public EstadoChangeEntity(Estado estado, Date fecha, SolicitudEntity solicitud) {
        this.estado = estado;
        this.fecha = fecha;
        this.solicitud = solicitud;
    }
}