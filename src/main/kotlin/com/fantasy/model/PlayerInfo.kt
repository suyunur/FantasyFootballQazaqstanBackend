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
