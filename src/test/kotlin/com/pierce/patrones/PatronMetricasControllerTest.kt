package com.pierce.patrones.controller

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.dto.PatronMetricasDTO
import com.pierce.patrones.mapper.Mapper
import com.pierce.patrones.model.PatronMetricas
import com.pierce.patrones.service.IPatronMetricasService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PatronMetricasControllerTest {

    @Mock
    private lateinit var patronMetricasService: IPatronMetricasService

    @Mock
    private lateinit var mapper: Mapper

    @InjectMocks
    private lateinit var patronMetricasController: PatronMetricasController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Devolver patronMetricas correctamente`() {
        val patronMetricasMock = PatronMetricas(id = 1L, cantidad_positivos = 23, cantidad_negativos = 2)
        val patronMetricasDTO = PatronMetricasDTO(cantidad_positivos = 23, cantidad_negativos = 2)

        `when`(patronMetricasService.getMetricas()).thenReturn(patronMetricasMock)
        `when`(mapper.toPatronMetricasDTO(patronMetricasMock)).thenReturn(patronMetricasDTO)

        val response: ResponseEntity<PatronMetricasDTO> = patronMetricasController.getEstadisticas()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(patronMetricasDTO, response.body)

        verify(patronMetricasService).getMetricas()
        verify(mapper).toPatronMetricasDTO(patronMetricasMock)

    }
    @Test
    fun `Devolver patronMetricas con valores en 0 cuando no hay datos`() {
        val patronMetricasMockVacio = PatronMetricas(id = 1L, cantidad_positivos = 0, cantidad_negativos = 0)
        val patronMetricasDTO = PatronMetricasDTO(cantidad_positivos = 0, cantidad_negativos = 0)

        `when`(patronMetricasService.getMetricas()).thenReturn(patronMetricasMockVacio)
        `when`(mapper.toPatronMetricasDTO(patronMetricasMockVacio)).thenReturn(patronMetricasDTO)

        val response: ResponseEntity<PatronMetricasDTO> = patronMetricasController.getEstadisticas()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(patronMetricasDTO, response.body)

        verify(patronMetricasService).getMetricas()
        verify(mapper).toPatronMetricasDTO(patronMetricasMockVacio)
    }
    @Test
    fun `Agregar valores a patronMetricas correctamente y devolver status 200`() {

        val adnDTO = AdnDTO(adn = listOf("0RH+", "0RH-", "0RH+", "2RH4", "0RH1"))

        val response: ResponseEntity<String> = patronMetricasController.addPatrones(adnDTO)

        verify(patronMetricasService).addMetricas(adnDTO)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("¡Se encontraron resultados!", response.body)
    }

    @Test
    fun `Devolver error 403 cuando ocurre una excepcion por no ingresar patrones correctos`() {

        val adnDTO = AdnDTO(adn = listOf("0RH4", "0RH1"))
        val exceptionMessage = "No se encontraron patrones válidos"

        doThrow(IllegalArgumentException(exceptionMessage)).`when`(patronMetricasService).addMetricas(adnDTO)

        val response: ResponseEntity<String> = patronMetricasController.addPatrones(adnDTO)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        assertEquals(exceptionMessage, response.body)

        verify(patronMetricasService).addMetricas(adnDTO)
    }


}
