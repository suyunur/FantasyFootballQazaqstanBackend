package com.fantasy.repository

import com.fantasy.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun findByUsername(username: String): Optional<User>
    fun findByPasswordResetToken(token: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}
