package com.pierce.patrones.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class PatronMetricas(
    @Id
    val id: Long,
    var cantidad_positivos: Int = 0,
    var cantidad_negativos: Int = 0

)