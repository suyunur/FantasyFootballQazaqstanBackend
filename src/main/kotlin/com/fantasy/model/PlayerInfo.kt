package com.fantasy.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "player_info",
    uniqueConstraints = [UniqueConstraint(name = "uk_player_info_user_id", columnNames = ["user_id"])]
)
data class PlayerInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team", nullable = false)
    val team: Team? = null,

    @Column(name = "average_points", nullable = false)
    val averagePoints: Int,

    @Column(name = "current_points", nullable = false)
    val currentPoints: Int,

    @Column(name = "highest_points", nullable = false)
    val highestPoints: Int,
) {
    companion object {
        fun empty(userId: Long) = PlayerInfo(
            userId = userId,
            team = null,
            averagePoints = 0,
            currentPoints = 0,
            highestPoints = 0
        )
    }
}
