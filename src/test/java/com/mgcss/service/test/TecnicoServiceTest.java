package com.mgcss.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.infrastructure.TecnicoRepository;
import com.mgcss.service.TecnicoService;

@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {

	@Mock
	private TecnicoRepository tecnicoRepository;
	
	@InjectMocks
	private TecnicoService tecnicoService;
	
	@Test
	void crearTecnicoSinEspecialidadTest() {
		Tecnico tecnicoEsperado = Tecnico.crearTecnico("Manuel");
        when(tecnicoRepository.save(any(Tecnico.class))).thenReturn(tecnicoEsperado);

        Tecnico resultado = tecnicoService.crearTecnicoSinEspecialidad("Manuel");

        assertNotNull(resultado);
        assertEquals("Manuel", resultado.getNombre());
        verify(tecnicoRepository, times(1)).save(any(Tecnico.class));
	}
	
	@Test
    void activarTecnicoTest() {
        // Arrange
        Long id = 1L;
        Tecnico tecnico = Tecnico.crearTecnico("Manuel"); //Inicialmente false ya que no tiene especialidad
        tecnico.setEspecialidad("Software");
        
        when(tecnicoRepository.findById(id)).thenReturn(Optional.of(tecnico));
        when(tecnicoRepository.save(any(Tecnico.class))).thenReturn(tecnico);

        // Act
        tecnicoService.activarTecnico(id);

        // Assert
        assertTrue(tecnico.isActivo(), "El técnico debería estar activo");
        verify(tecnicoRepository).save(tecnico);
    }
	
	@Test
	void obtenerTecnicoTest() {
		Long idExistente = 1L;
		Long idNoExistente = 100L;
		Tecnico tecnico = Tecnico.crearTecnico("Manuel", "Software");
		
		when(tecnicoRepository.findById(idExistente)).thenReturn(Optional.of(tecnico));
		when(tecnicoRepository.findById(idNoExistente)).thenReturn(Optional.empty());
		
		Tecnico resultadoExiste = tecnicoService.obtenerTecnico(idExistente);
		Tecnico resultadoNoExiste = tecnicoService.obtenerTecnico(idNoExistente);
		
		assertNotNull(resultadoExiste);
		assertEquals(tecnico, resultadoExiste);
		assertNull(resultadoNoExiste);
		
		verify(tecnicoRepository, times(1)).findById(idExistente);
		verify(tecnicoRepository, times(1)).findById(idNoExistente);
	}
	

}
