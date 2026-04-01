package com.fantasy.model

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "players")
data class PlayerInfo(
    val email: String,
    val teamName: String,
    val averagePoints: Int,
    val currentPoints: Int,
    val highestPoints: Int,
) {
    companion object {
        fun empty(email: String) = PlayerInfo(
            email = email,
            teamName = "",
            averagePoints = 0,
            currentPoints = 0,
            highestPoints = 0
        )
    }
}
