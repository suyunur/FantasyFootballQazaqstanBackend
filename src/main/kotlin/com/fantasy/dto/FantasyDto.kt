package com.fantasy.dto

data class FantasyInfoResponse(
    val number: Int,
    val teamName: String,
    val lastGameDate: Long,
    val averagePoints: Int,
    val currentPoints: Int,
    val highestPoints: Int,
    val transferDeadline: Long?
)