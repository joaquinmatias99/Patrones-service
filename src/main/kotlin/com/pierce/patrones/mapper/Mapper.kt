package com.pierce.patrones.mapper

import com.pierce.patrones.dto.PatronMetricasDTO
import com.pierce.patrones.model.PatronMetricas
import org.springframework.stereotype.Component

@Component
object Mapper {
    fun toPatronMetricasDTO(patronMetricas: PatronMetricas): PatronMetricasDTO {
        return PatronMetricasDTO(
            cantidad_positivos = patronMetricas.cantidad_positivos,
            cantidad_negativos = patronMetricas.cantidad_negativos
        )
    }
}
