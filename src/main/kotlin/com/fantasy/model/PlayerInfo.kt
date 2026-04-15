package com.fantasy.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "players")
data class PlayerInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long,
    val teamName: String,
    val averagePoints: Int,
    val currentPoints: Int,
    val highestPoints: Int,
) {
    companion object {
        fun empty(userId: Long) = PlayerInfo(
            userId = userId,
            teamName = "",
            averagePoints = 0,
            currentPoints = 0,
            highestPoints = 0
        )
    }
}
