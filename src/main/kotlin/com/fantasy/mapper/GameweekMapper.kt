package com.fantasy.mapper

import com.fantasy.dto.GameWeekInfoResponse
import com.fantasy.model.Gameweek

fun Gameweek.toGameWeekInfoResponse(): GameWeekInfoResponse =
    GameWeekInfoResponse(
        gameWeek = gameWeek,
        transferDeadline = transferDeadline
    )
