package com.pierce.patrones.service

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.model.Patron
import com.pierce.patrones.repository.IPatronRepository
import org.springframework.stereotype.Service

@Service
class PatronService(private val patronRepository: IPatronRepository) : IPatronService {

    override fun addPatrones(adnDTO: AdnDTO) {
        val patrones: List<String> = adnDTO.adn

        var contadorPositivo = 0
        var contadorNegativo = 0

        //Para mejor legibilidad podriamos usar el metodo "count" de las listas del lenguaje kotlin pero seria recorrrer dos veces.
        for (patron in patrones) {
            when (patron) {
                "0RH+" -> contadorPositivo++
                "0RH-" -> contadorNegativo++
            }
        }
        if (contadorPositivo == 0 && contadorNegativo == 0) {
            throw IllegalArgumentException("No se encontraron patrones válidos")
        }
        sumarContadores(contadorPositivo, contadorNegativo)
    }

    override fun getEstadisticas(): Patron {
        return patronRepository.findById(1L).orElseGet {
            Patron(1L, 0, 0)
        }
    }


    private fun sumarContadores(contadorPositivo: Int, contadorNegativo: Int) {
        //Obtenemos el patron, en caso de no haber, creamos uno vacio
        val patron = patronRepository.findById(1L).orElseGet { Patron(1L, 0, 0) }
        patron.cantidad_positivos += contadorPositivo
        patron.cantidad_negativos += contadorNegativo

        patronRepository.save(patron)

    }


}