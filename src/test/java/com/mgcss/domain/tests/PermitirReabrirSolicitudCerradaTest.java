package com.mgcss.domain.tests;

import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.domain.enums.Estado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermitirReabrirSolicitudCerradaTest {

    @Test
    public void reabrir_SolicitudCerrada_PasaAEnProceso() {
        // 1. Crear solicitud
        Solicitud solicitud = new Solicitud();
        
        // 2. Cambiar a EN_PROCESO (Ajusta este método según cómo esté implementado en tu clase Solicitud)
        solicitud.setEstado(Estado.EN_PROCESO); 
        
        // 3. Cerrar
        solicitud.cerrar();
        
        // 4. Reabrir
        solicitud.reabrir(); 
        
        // 5. Verificar estado final
        assertEquals(Estado.EN_PROCESO, solicitud.getEstado());
    }
}