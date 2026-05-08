package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@NoArgsConstructor // Usamos Lombok para el constructor vacío como en tus notas de refactorización
public class ClienteEntity {

    @Id
    private Long id;
    private String nombre;
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipo;

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public TipoCliente getTipo() { return tipo; }
    public void setTipo(TipoCliente tipo) { this.tipo = tipo; }

    // --- Mapeo Hexagonal ---

    public static ClienteEntity fromDomain(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNombre(cliente.getNombre());
        entity.setEmail(cliente.getEmail());
        entity.setTipo(cliente.getTipo());
        return entity;
    }

    public Cliente toDomain() {
        // Al reconstruir el dominio, usamos los datos de la entidad
        Cliente cliente = new Cliente(this.nombre, this.email, this.tipo);
        
        // Uso el setter para asignar el ID de la BD
        cliente.setId(this.id); 
        
        return cliente;
    }
}