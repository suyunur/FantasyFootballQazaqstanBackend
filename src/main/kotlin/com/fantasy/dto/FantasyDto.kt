package com.fantasy.dto

import com.fantasy.model.Team

data class FantasyInfoResponse(
    val playerInfo: PlayerInfoResponse,
    val gameWeekInfo: GameWeekInfoResponse,
)

data class PlayerInfoResponse(
    val teamId: String,
    val team: Team?,
    val averagePoints: Int,
    val currentPoints: Int,
    val highestPoints: Int,
)

data class GameWeekInfoResponse(
    val gameWeek: Int,
    val transferDeadline: Long)