package com.pierce.patrones

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.model.Patron
import com.pierce.patrones.repository.IPatronRepository
import com.pierce.patrones.service.PatronService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class PatronServiceTest {

    @Mock
    private lateinit var patronRepository: IPatronRepository

    @InjectMocks
    private lateinit var patronService: PatronService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Contar positivos y negativos correctamente`() {
        val adnDTO = AdnDTO(adn = listOf("0RH+", "0RH-", "0RH+", "2RH4", "0RH1"))
        val patronMock = Patron(id = 1L, cantidad_positivos = 0, cantidad_negativos = 0)


        `when`(patronRepository.findById(1L)).thenReturn(Optional.of(patronMock))

        patronService.addPatrones(adnDTO)

        assertEquals(2, patronMock.cantidad_positivos)
        assertEquals(1, patronMock.cantidad_negativos)

        verify(patronRepository).save(patronMock)
    }

    @Test
    fun `Lanzar illegalArgument Exception cuando no se encuentra algún patron  valido`() {
        val adnDTO = AdnDTO(adn = listOf("2RH4", "0RH1"))

        val exception = assertThrows<IllegalArgumentException> {
            patronService.addPatrones(adnDTO)
        }
        assertEquals("No se encontraron patrones válidos", exception.message)
    }

    @Test
    fun `Devolver patron con estadisticas`() {
        val patronMock = Patron(id = 1L, cantidad_positivos = 23, cantidad_negativos = 2)

        `when`(patronRepository.findById(1L)).thenReturn(Optional.of(patronMock))

        val resultado = patronService.getEstadisticas()

        assertEquals(23, resultado.cantidad_positivos)
        assertEquals(2, resultado.cantidad_negativos)

        verify(patronRepository).findById(1L)
    }


    @Test
    fun `Devolver patron inicial, sin datos`() {
        `when`(patronRepository.findById(1L)).thenReturn(Optional.empty())

        val resultado = patronService.getEstadisticas()
        assertEquals(Patron(1L, 0, 0), resultado)

        verify(patronRepository).findById(1L)
    }
}
