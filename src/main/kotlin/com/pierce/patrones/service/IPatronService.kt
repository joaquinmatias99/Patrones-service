package com.pierce.patrones.service

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.model.Patron

interface IPatronService {
    fun addPatrones(adnDTO: AdnDTO)

    fun getEstadisticas(): Patron
}