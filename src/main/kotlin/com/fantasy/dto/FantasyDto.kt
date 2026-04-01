package com.fantasy.dto

import com.fantasy.model.GameWeekInfo
import com.fantasy.model.PlayerInfo

data class FantasyInfoResponse(
    val playerInfo: PlayerInfo? = null,
    val gameWeekInfo: GameWeekInfo,
)

data class PlayerInfoResponse(
    val teamId: String,
    val teamName: String,
    val averagePoints: Int,
    val currentPoints: Int,
    val highestPoints: Int,
)

data class GameWeekInfoResponse(
    val gameWeek: Int,
    val lastGameDate: Long,
    val transferDeadline: Long?
)