package com.fantasy.service

import com.fantasy.dto.FantasyInfoResponse
import com.fantasy.dto.PlayerInfoResponse
import com.fantasy.exception.AuthException
import com.fantasy.mapper.toGameWeekInfoResponse
import com.fantasy.model.PlayerInfo
import com.fantasy.repository.FantasyRepository
import com.fantasy.repository.GameweekRepository
import com.fantasy.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class FantasyService(
    private val fantasyRepository: FantasyRepository,
    private val userRepository: UserRepository,
    private val gameweekRepository: GameweekRepository
) {

    fun getInfo(email: String) : FantasyInfoResponse {
        val user = userRepository.findByEmail(email)
            .orElseThrow { AuthException("User not found") }

        val gameWeekInfo = gameweekRepository.findFirstByOrderByGameWeekDesc()
            .orElseThrow { AuthException("No gameweek configured") }
            .toGameWeekInfoResponse()
        val playerInfo = fantasyRepository.findByUserId(user.id).orElseGet {
            fantasyRepository.save(PlayerInfo.empty(user.id))
        }
        val info = FantasyInfoResponse(
            playerInfo = playerInfo.toResponse(),
            gameWeekInfo = gameWeekInfo
        )
        return info
    }

    private fun PlayerInfo.toResponse() = PlayerInfoResponse(
        teamId = this.id.toString(),
        team = this.team,
        averagePoints = this.averagePoints,
        currentPoints = this.currentPoints,
        highestPoints = this.highestPoints
    )
}