package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.tecnico.Tecnico;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tecnicos")
@NoArgsConstructor
public class TecnicoEntity {

    @Id
    private Long id;
    private String nombre;
    private String especialidad;
    private boolean activo;

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

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