package com.fantasy.repository

import com.fantasy.dto.FantasyInfoResponse
import com.fantasy.model.PlayerInfo
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface FantasyRepository {
    fun getInfo(email: String): Optional<FantasyInfoResponse>
    fun getPlayerFantasy(email: String): Optional<PlayerInfo>
}