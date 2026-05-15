package com.mgcss.infrastructure.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.enums.Estado;
import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.infrastructure.SolicitudRepository;
import com.mgcss.infrastructure.persistence.SolicitudRepositoryAdapter;

@DataJpaTest
@ActiveProfiles("test") // Usa tu application-test.yml
@Import(SolicitudRepositoryAdapter.class) // Carga el adaptador manualmente
class JpaSolicitudRepositoryTest {

    @Autowired
    private SolicitudRepository repository; // Inyectamos el puerto, no JPA directo

    @Test
    void testGuardarYRecuperarSolicitud() {
        // 1. Guardar entidad
        Solicitud nuevaSolicitud = new Solicitud();
        nuevaSolicitud.setDescripcion("Problema con el servidor");
        nuevaSolicitud.setEstado(Estado.ABIERTA);
        
        Solicitud guardada = repository.save(nuevaSolicitud);

        // 2. Recuperarla
        Solicitud recuperada = repository.findById(guardada.getId()).orElse(null);

        // 3. Verificar integridad
        assertNotNull(recuperada, "La solicitud debe existir en BD");
        assertEquals(guardada.getId(), recuperada.getId());
        assertEquals("Problema con el servidor", recuperada.getDescripcion());
        assertEquals(Estado.ABIERTA, recuperada.getEstado());
    }
    
    @Test
    void testFindAll() {
        Solicitud sol1 = new Solicitud();
        sol1.setDescripcion("Fallo 1");
        repository.save(sol1);
        
        Solicitud sol2 = new Solicitud();
        sol2.setDescripcion("Fallo 2");
        repository.save(sol2);

        List<Solicitud> lista = repository.findAll();

        assertNotNull(lista);
        assertEquals(2, lista.size(), "Debería recuperar 2 solicitudes de la BD");
    }
}