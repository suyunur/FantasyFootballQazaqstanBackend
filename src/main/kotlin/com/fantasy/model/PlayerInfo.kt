package com.fantasy.model

import jakarta.persistence.*

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

    @Column(name = "current_points", nullable = false)
    val currentPoints: Int,
) {
    companion object {
        fun empty(userId: Long) = PlayerInfo(
            userId = userId,
            team = null,
            currentPoints = 0,
        )
    }
}
