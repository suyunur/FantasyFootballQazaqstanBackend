package com.fantasy.repository

import com.fantasy.model.PlayerInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FantasyRepository : JpaRepository<PlayerInfo, Long> {
    fun findByEmail(email: String): Optional<PlayerInfo>
}