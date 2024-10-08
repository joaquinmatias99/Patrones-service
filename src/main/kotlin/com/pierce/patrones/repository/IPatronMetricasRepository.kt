package com.pierce.patrones.repository

import com.pierce.patrones.model.PatronMetricas
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPatronMetricasRepository : JpaRepository<PatronMetricas,Long>{
}