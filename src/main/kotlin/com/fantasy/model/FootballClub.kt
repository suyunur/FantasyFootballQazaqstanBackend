package com.fantasy.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "football_club")
data class FootballClub(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(name = "short_name", nullable = false)
    val shortName: String,

    @Column(name = "logo_url", nullable = false)
    val logoUrl: String,

    @Column(name = "position")
    val position: Int = 0,

    @Column(name = "played")
    val played: Int = 0,

    @Column(name = "wins")
    val wins: Int = 0,

    @Column(name = "draws")
    val draws: Int = 0,

    @Column(name = "losses")
    val losses: Int = 0,

    @Column(name = "goals_for")
    val goalsFor: Int = 0,

    @Column(name = "goals_against")
    val goalsAgainst: Int = 0,

    @Column(name = "goal_difference")
    val goalDifference: Int = 0,

    @Column(name = "points")
    val points: Int = 0,

    @Column(name = "form")
    val form: String = "", // e.g. "WWDLW"

    @Column(name = "strength")
    val strength: Int = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_opponent_id")
    val nextOpponent: FootballClub? = null
)
