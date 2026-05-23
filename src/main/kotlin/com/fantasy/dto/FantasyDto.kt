package com.fantasy.dto

import com.fantasy.model.Team

data class FantasyInfoResponse(
    val playerInfo: PlayerInfoResponse,
    val gameWeekInfo: GameWeekInfoResponse,
)

data class PlayerInfoResponse(
    val teamId: String,
    val team: Team?,
    val currentPoints: Int,
)

data class GameWeekInfoResponse(
    val gameWeek: Int,
    val transferDeadline: Long)

data class ChangeTeamRequest(
    val team: Team,
    val deductedPoints: Int
)