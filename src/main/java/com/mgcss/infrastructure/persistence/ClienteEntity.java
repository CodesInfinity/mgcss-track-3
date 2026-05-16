package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "clientes")
@NoArgsConstructor // Usamos Lombok para el constructor vacío como en tus notas de refactorización
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipo;

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