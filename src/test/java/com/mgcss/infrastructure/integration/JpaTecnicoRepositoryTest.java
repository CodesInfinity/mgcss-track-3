package com.mgcss.infrastructure.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.infrastructure.TecnicoRepository;
import com.mgcss.infrastructure.persistence.TecnicoRepositoryAdapter;

@DataJpaTest
@ActiveProfiles("test") // Usa tu application-test.yml
@Import(TecnicoRepositoryAdapter.class) // Carga el adaptador manualmente
class JpaTecnicoRepositoryTest {

    @Autowired
    private TecnicoRepository repository; // Inyectamos el puerto

    @Test
    void testGuardarYRecuperarTecnico() {
        // 1. Guardar entidad
        Tecnico nuevoTecnico = Tecnico.crearTecnico("Carlos", "Redes");
        Tecnico guardado = repository.save(nuevoTecnico);

        // 2. Recuperarla
        Tecnico recuperado = repository.findById(guardado.getId()).orElse(null);

        // 3. Verificar integridad
        assertNotNull(recuperado, "El técnico debe existir en BD");
        assertEquals(guardado.getId(), recuperado.getId());
        assertEquals("Carlos", recuperado.getNombre());
        assertEquals("Redes", recuperado.getEspecialidad());
        assertTrue(recuperado.isActivo(), "El técnico debería estar activo al tener especialidad");
    }
}