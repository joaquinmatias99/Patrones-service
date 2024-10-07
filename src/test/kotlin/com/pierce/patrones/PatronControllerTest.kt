package com.pierce.patrones.controller

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.dto.PatronDTO
import com.pierce.patrones.mapper.Mapper
import com.pierce.patrones.model.Patron
import com.pierce.patrones.service.IPatronService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PatronControllerTest {

    @Mock
    private lateinit var patronService: IPatronService

    @Mock
    private lateinit var mapper: Mapper

    @InjectMocks
    private lateinit var patronController: PatronController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Devolver estadisticas con datos correctamente`() {
        val patronMock = Patron(id = 1L, cantidad_positivos = 23, cantidad_negativos = 2)
        val patronDTO = PatronDTO(cantidad_positivos = 23, cantidad_negativos = 2)

        `when`(patronService.getEstadisticas()).thenReturn(patronMock)
        `when`(mapper.toPatronDTO(patronMock)).thenReturn(patronDTO)

        val response: ResponseEntity<PatronDTO> = patronController.getEstadisticas()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(patronDTO, response.body)

        verify(patronService).getEstadisticas()
        verify(mapper).toPatronDTO(patronMock)

    }
    @Test
    fun `Devolver estadisticas vacias cuando no hay datos`() {
        val patronMockVacio = Patron(id = 1L, cantidad_positivos = 0, cantidad_negativos = 0)
        val patronDTO = PatronDTO(cantidad_positivos = 0, cantidad_negativos = 0)

        `when`(patronService.getEstadisticas()).thenReturn(patronMockVacio)
        `when`(mapper.toPatronDTO(patronMockVacio)).thenReturn(patronDTO)

        val response: ResponseEntity<PatronDTO> = patronController.getEstadisticas()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(patronDTO, response.body)

        verify(patronService).getEstadisticas()
        verify(mapper).toPatronDTO(patronMockVacio)
    }
    @Test
    fun `Agregar patrones correctamente y devolver status 200`() {

        val adnDTO = AdnDTO(adn = listOf("0RH+", "0RH-", "0RH+", "2RH4", "0RH1"))

        val response: ResponseEntity<String> = patronController.addPatrones(adnDTO)

        verify(patronService).addPatrones(adnDTO)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("¡Se encontraron resultados!", response.body)
    }

    @Test
    fun `Devolver error 403 cuando ocurre una excepcion por no ingresar patrones correctos`() {

        val adnDTO = AdnDTO(adn = listOf("0RH4", "0RH1"))
        val exceptionMessage = "No se encontraron patrones válidos"

        doThrow(IllegalArgumentException(exceptionMessage)).`when`(patronService).addPatrones(adnDTO)

        val response: ResponseEntity<String> = patronController.addPatrones(adnDTO)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        assertEquals(exceptionMessage, response.body)

        verify(patronService).addPatrones(adnDTO)
    }


}
