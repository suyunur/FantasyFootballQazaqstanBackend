package com.fantasy.repository

import com.fantasy.model.Footballer
import com.fantasy.model.Position
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FootballerRepository : JpaRepository<Footballer, Long> {
    fun findByPosition(position: Position): List<Footballer>
}