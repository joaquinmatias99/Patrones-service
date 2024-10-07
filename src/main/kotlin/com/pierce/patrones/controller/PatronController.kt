package com.pierce.patrones.controller

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.dto.PatronDTO
import com.pierce.patrones.mapper.Mapper
import com.pierce.patrones.service.IPatronService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class PatronController(
    private val patronService: IPatronService,
    private val mapper:Mapper
) {
    @GetMapping("/stats")
    fun getEstadisticas(): ResponseEntity<PatronDTO> {
        val patron = patronService.getEstadisticas()
        val patronDTO = mapper.toPatronDTO(patron)
        return ResponseEntity.ok(patronDTO)
    }

    @PostMapping("/validate")
    fun addPatrones(@RequestBody adn: AdnDTO): ResponseEntity<String> {
        return try {
            patronService.addPatrones(adn)
            ResponseEntity.ok("¡Se encontraron resultados!")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message ?: "Error")
        }
    }
}