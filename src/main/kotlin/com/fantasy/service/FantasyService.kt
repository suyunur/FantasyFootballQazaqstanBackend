package com.fantasy.service

import com.fantasy.dto.FantasyInfoResponse
import com.fantasy.model.GameWeekInfo
import com.fantasy.model.PlayerInfo
import com.fantasy.repository.FantasyRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class FantasyService(
    private val fantasyRepository: FantasyRepository
) {

    fun getInfo(email: String) : FantasyInfoResponse {
        val gameWeekInfo = GameWeekInfo(
            gameWeek = 1,
            lastGameDate = Date().time,
            transferDeadline = Date().time,
        )
        val playerInfo = fantasyRepository.getPlayerFantasy(email).orElseGet {
            PlayerInfo.empty(email)
        }
        val info = FantasyInfoResponse(
            playerInfo = playerInfo,
            gameWeekInfo = gameWeekInfo
        )
        return info
    }
}