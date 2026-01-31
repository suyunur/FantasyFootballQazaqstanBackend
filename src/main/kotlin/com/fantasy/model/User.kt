package com.fantasy.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false)
    var password: String,

    @Column(name = "first_name")
    val firstName: String? = null,

    @Column(name = "last_name")
    val lastName: String? = null,

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,

    @Column(name = "is_enabled")
    val isEnabled: Boolean = true,

    @Column(name = "is_account_non_expired")
    val isAccountNonExpired: Boolean = true,

    @Column(name = "is_account_non_locked")
    val isAccountNonLocked: Boolean = true,

    @Column(name = "is_credentials_non_expired")
    val isCredentialsNonExpired: Boolean = true,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "password_reset_token")
    var passwordResetToken: String? = null,

    @Column(name = "password_reset_token_expiry")
    var passwordResetTokenExpiry: LocalDateTime? = null
)

enum class Role {
    USER,
    ADMIN
}
