package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.tecnico.Tecnico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tecnicos")
@NoArgsConstructor
public class TecnicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String especialidad;
    private boolean activo;

    // --- Mapeo Hexagonal ---
    public static TecnicoEntity fromDomain(Tecnico tecnico) {
        TecnicoEntity entity = new TecnicoEntity();
        entity.setId(tecnico.getId());
        entity.setNombre(tecnico.getNombre());
        entity.setEspecialidad(tecnico.getEspecialidad());
        entity.setActivo(tecnico.isActivo());
        return entity;
    }

    public Tecnico toDomain() {
        Tecnico tecnico;
        
        // Reconstruimos usando tu lógica de dominio
        if (this.especialidad != null) {
            tecnico = Tecnico.crearTecnico(this.nombre, this.especialidad);
        } else {
            tecnico = Tecnico.crearTecnico(this.nombre);
        }
        
        // Sobrescribimos con los datos persistidos
        tecnico.setId(this.id);
        tecnico.setActivo(this.activo);
        
        return tecnico;
    }
}