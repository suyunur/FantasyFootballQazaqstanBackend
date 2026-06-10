package com.fantasy.service

import com.fantasy.dto.ChangeTeamRequest
import com.fantasy.dto.FantasyInfoResponse
import com.fantasy.dto.PlayerInfoResponse
import com.fantasy.exception.AuthException
import com.fantasy.mapper.toGameWeekInfoResponse
import com.fantasy.model.Footballer
import com.fantasy.model.PlayerInfo
import com.fantasy.model.Position
import com.fantasy.model.Team
import com.fantasy.repository.FantasyRepository
import com.fantasy.repository.FootballerRepository
import com.fantasy.repository.GameweekRepository
import com.fantasy.repository.TeamRepository
import com.fantasy.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Service
class FantasyService(
    private val fantasyRepository: FantasyRepository,
    private val teamRepository: TeamRepository,
    private val userRepository: UserRepository,
    private val gameweekRepository: GameweekRepository,
    private val footballerRepository: FootballerRepository,
) {

    fun getInfo(email: String) : FantasyInfoResponse {
        val user = userRepository.findByEmail(email)
            .orElseThrow { AuthException("User not found") }

        val gameWeekInfo = gameweekRepository.findFirstByOrderByGameWeekDesc()
            .orElseThrow { AuthException("No gameweek configured") }
            .toGameWeekInfoResponse()
        val playerInfo = fantasyRepository.findByUserId(user.id).orElseGet {
            fantasyRepository.save(PlayerInfo.empty(user.id))
        }.toResponse()
        val info = FantasyInfoResponse(
            playerInfo = playerInfo,
            gameWeekInfo = gameWeekInfo
        )
        return info
    }

    fun changeTeam(email: String, request: ChangeTeamRequest) {
        val user = userRepository.findByEmail(email)
            .orElseThrow { AuthException("User not found") }
        val currentTeam = teamRepository.findById(user.id).getOrElse {
            saveTeam(request.team.copy(currentPoints = 0))
            return
        }
        val deductedPoints = countChangedPlayers(currentTeam.footballers, request.team.footballers)
        saveTeam(request.team.copy(currentPoints = currentTeam.currentPoints - deductedPoints))
    }

    private fun saveTeam(team: Team) {
        teamRepository.save(team)
    }

    private fun PlayerInfo.toResponse() = PlayerInfoResponse(
        teamId = this.id.toString(),
        team = this.team,
        currentPoints = this.currentPoints,
    )

    @Transactional(readOnly = true)
    fun findFootballersByPosition(position: Position): List<Footballer> {
        return footballerRepository.findByPosition(position)
    }

    private fun countChangedPlayers(
        oldSquad: List<Footballer>,
        newSquad: List<Footballer>
    ): Int {

        val oldIds = oldSquad.map { it.id }.toSet()
        val newIds = newSquad.map { it.id }.toSet()

        val added = newIds - oldIds
        val removed = oldIds - newIds

        return added.size + removed.size
    }
}