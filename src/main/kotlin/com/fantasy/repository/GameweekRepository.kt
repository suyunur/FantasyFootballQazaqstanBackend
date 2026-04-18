package com.fantasy.repository

import com.fantasy.model.Gameweek
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GameweekRepository : JpaRepository<Gameweek, Long> {
    fun findFirstByOrderByGameWeekDesc(): Optional<Gameweek>
}
