package com.pierce.patrones.controller

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.dto.PatronMetricasDTO
import com.pierce.patrones.mapper.Mapper
import com.pierce.patrones.service.IPatronMetricasService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class PatronMetricasController(
    private val patronMetricasService: IPatronMetricasService,
    private val mapper:Mapper
) {
    @GetMapping("/stats")
    fun getEstadisticas(): ResponseEntity<PatronMetricasDTO> {
        val patron = patronMetricasService.getMetricas()
        val patronDTO = mapper.toPatronMetricasDTO(patron)
        return ResponseEntity.ok(patronDTO)
    }

    @PostMapping("/validate")
    fun addPatrones(@RequestBody adn: AdnDTO): ResponseEntity<String> {
        return try {
            patronMetricasService.addMetricas(adn)
            ResponseEntity.ok("¡Se encontraron resultados!")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message ?: "Error")
        }
    }
}