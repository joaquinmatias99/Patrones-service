package com.pierce.patrones.repository

import com.pierce.patrones.model.Patron
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPatronRepository : JpaRepository<Patron,Long>{
}