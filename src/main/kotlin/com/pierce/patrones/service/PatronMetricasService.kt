package com.pierce.patrones.service

import com.pierce.patrones.dto.AdnDTO
import com.pierce.patrones.model.PatronMetricas
import com.pierce.patrones.repository.IPatronMetricasRepository
import org.springframework.stereotype.Service

@Service
class PatronMetricasService(private val patronMetricasRepository: IPatronMetricasRepository) : IPatronMetricasService {

    override fun addMetricas(adnDTO: AdnDTO) {
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

    override fun getMetricas(): PatronMetricas {
        return patronMetricasRepository.findById(1L).orElseGet {
            PatronMetricas(1L, 0, 0)
        }
    }


    private fun sumarContadores(contadorPositivo: Int, contadorNegativo: Int) {
        //Obtenemos el patronMetricas, en caso de no haber, creamos uno vacio
        val patronMetricas = patronMetricasRepository.findById(1L).orElseGet { PatronMetricas(1L, 0, 0) }
        patronMetricas.cantidad_positivos += contadorPositivo
        patronMetricas.cantidad_negativos += contadorNegativo

        patronMetricasRepository.save(patronMetricas)

    }


}