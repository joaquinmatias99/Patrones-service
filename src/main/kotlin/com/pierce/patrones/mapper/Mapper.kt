package com.pierce.patrones.mapper

import com.pierce.patrones.dto.PatronDTO
import com.pierce.patrones.model.Patron

object Mapper {
    fun toPatronDTO(patron: Patron): PatronDTO {
        return PatronDTO(
            cantidad_positivos = patron.cantidad_positivos,
            cantidad_negativos = patron.cantidad_negativos
        )
    }
}
