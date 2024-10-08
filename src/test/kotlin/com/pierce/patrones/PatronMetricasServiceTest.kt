package com.pierce.patrones

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.model.PatronMetricas
import com.pierce.patrones.repository.IPatronMetricasRepository
import com.pierce.patrones.service.PatronMetricasService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class PatronMetricasServiceTest {

    @Mock
    private lateinit var patronMetricasRepository: IPatronMetricasRepository

    @InjectMocks
    private lateinit var patronMetricasService: PatronMetricasService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Contar positivos y negativos correctamente`() {
        val adnDTO = AdnDTO(adn = listOf("0RH+", "0RH-", "0RH+", "2RH4", "0RH1"))
        val patronMetricasMock = PatronMetricas(id = 1L, cantidad_positivos = 0, cantidad_negativos = 0)


        `when`(patronMetricasRepository.findById(1L)).thenReturn(Optional.of(patronMetricasMock))

        patronMetricasService.addMetricas(adnDTO)

        assertEquals(2, patronMetricasMock.cantidad_positivos)
        assertEquals(1, patronMetricasMock.cantidad_negativos)

        verify(patronMetricasRepository).save(patronMetricasMock)
    }

    @Test
    fun `Lanzar illegalArgument Exception cuando no se encuentra algún patron  valido`() {
        val adnDTO = AdnDTO(adn = listOf("2RH4", "0RH1"))

        val exception = assertThrows<IllegalArgumentException> {
            patronMetricasService.addMetricas(adnDTO)
        }
        assertEquals("No se encontraron patrones válidos", exception.message)
    }

    @Test
    fun `Devolver las metricas de un patron`() {
        val patronMetricasMock = PatronMetricas(id = 1L, cantidad_positivos = 23, cantidad_negativos = 2)

        `when`(patronMetricasRepository.findById(1L)).thenReturn(Optional.of(patronMetricasMock))

        val resultado = patronMetricasService.getMetricas()

        assertEquals(23, resultado.cantidad_positivos)
        assertEquals(2, resultado.cantidad_negativos)

        verify(patronMetricasRepository).findById(1L)
    }


    @Test
    fun `Devolver patron inicial, sin datos`() {
        `when`(patronMetricasRepository.findById(1L)).thenReturn(Optional.empty())

        val resultado = patronMetricasService.getMetricas()
        assertEquals(PatronMetricas(1L, 0, 0), resultado)

        verify(patronMetricasRepository).findById(1L)
    }
}
