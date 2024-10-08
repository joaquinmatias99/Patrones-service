package com.pierce.patrones.service

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.model.PatronMetricas

interface IPatronMetricasService {
    fun addMetricas(adnDTO: AdnDTO)

    fun getMetricas(): PatronMetricas

    fun resetearMetricas()
}