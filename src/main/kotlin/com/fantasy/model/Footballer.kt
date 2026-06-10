package com.fantasy.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "footballer")
data class Footballer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(name = "second_name", nullable = false)
    val secondName: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val position: Position,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    val club: FootballClub,

    @Column(name = "game_week_points", nullable = false)
    val gameWeekPoints: Int,

    @Column(name = "selected_percentage", nullable = false)
    val selectedPercentage: Float,

    @Column(name = "current_price", nullable = false)
    val currentPrice: Double,
)

enum class Position {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD
}
