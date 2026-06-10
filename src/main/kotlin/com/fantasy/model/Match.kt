package com.fantasy.model
import jakarta.persistence.*

@Entity
@Table(name = "matches")
data class Match(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val date: String,

    val time: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    val homeTeam: FootballClub,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    val awayTeam: FootballClub,

    val homeScore: Int? = null,
    val awayScore: Int? = null,

    val homePosition: Int? = null,
    val awayPosition: Int? = null,

    @Enumerated(EnumType.STRING)
    val status: MatchStatus
)

enum class MatchStatus {
    SCHEDULED,
    LIVE,
    FINISHED,
    POSTPONED,
    CANCELED,
    INTERRUPTED
}