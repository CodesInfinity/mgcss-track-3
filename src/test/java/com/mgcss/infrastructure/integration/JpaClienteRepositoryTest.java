package com.mgcss.infrastructure.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.TipoCliente;
import com.mgcss.infrastructure.ClienteRepository;
import com.mgcss.infrastructure.persistence.ClienteRepositoryAdapter;
import com.mgcss.infrastructure.persistence.JpaClienteRepository;
import com.mgcss.infrastructure.persistence.ClienteEntity;

@DataJpaTest
@ActiveProfiles("test") // Usa tu application-test.yml
@Import(ClienteRepositoryAdapter.class) // Carga el adaptador manualmente
class JpaClienteRepositoryTest {

    @Autowired
    private ClienteRepository repositoryAdapter; // Puerto Hexagonal

    @Autowired
    private JpaClienteRepository jpaRepository; // Repositorio Spring Data para comprobar BD

    @Test
    void testGuardarYRecuperarCliente() {
        // 1. Crear y guardar cliente a través de nuestro puerto
        Cliente nuevoCliente = new Cliente("Ana", "ana@example.com", TipoCliente.PREMIUM);
        Cliente guardado = repositoryAdapter.save(nuevoCliente);

        // 2. Recuperar directamente con JPA para verificar que se persistió
        ClienteEntity recuperado = jpaRepository.findById(guardado.getId()).orElse(null);

        // 3. Verificar integridad
        assertNotNull(recuperado, "El cliente debe existir en la base de datos");
        assertEquals("Ana", recuperado.getNombre());
        assertEquals("ana@example.com", recuperado.getEmail());
        assertEquals(TipoCliente.PREMIUM, recuperado.getTipo());
    }
}