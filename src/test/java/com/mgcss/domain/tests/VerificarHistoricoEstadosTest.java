package com.mgcss.domain.tests;

import com.mgcss.domain.solicitud.Solicitud;
import com.mgcss.domain.enums.Estado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerificarHistoricoEstadosTest {

    @Test
    public void cambiarEstado_RegistraEnHistorico() {
        Solicitud solicitud = new Solicitud();
        
        // El histórico debería registrar cada cambio
        solicitud.setEstado(Estado.EN_PROCESO);
        solicitud.cerrar();
        solicitud.reabrir();
        
        // Fallará porque getHistorico() no existe en la entidad Solicitud
        assertEquals(3, solicitud.getHistorico().size());
    }
}