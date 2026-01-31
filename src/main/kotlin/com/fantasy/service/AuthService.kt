package com.fantasy.service

import com.fantasy.dto.*
import com.fantasy.exception.AuthException
import com.fantasy.model.Role
import com.fantasy.model.User
import com.fantasy.repository.UserRepository
import com.fantasy.security.JwtService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    @Value("\${jwt.expiration}")
    private val jwtExpiration: Long
) {

    @Transactional
    fun register(request: RegisterRequest): AuthResponse {
        // Check if email already exists
        if (userRepository.existsByEmail(request.email)) {
            throw AuthException("Email already registered")
        }

        // Check if username already exists
        if (userRepository.existsByUsername(request.username)) {
            throw AuthException("Username already taken")
        }

        // Create new user
        val user = User(
            email = request.email,
            username = request.username,
            password = passwordEncoder.encode(request.password),
            firstName = request.firstName,
            lastName = request.lastName,
            role = Role.USER
        )

        val savedUser = userRepository.save(user)

        // Generate tokens
        val userDetails = userDetailsService.loadUserByUsername(savedUser.email)
        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtExpiration,
            user = savedUser.toUserResponse()
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        // Authenticate user
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val user = userRepository.findByEmail(request.email)
            .orElseThrow { AuthException("Invalid email or password") }

        // Generate tokens
        val userDetails = userDetailsService.loadUserByUsername(user.email)
        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtExpiration,
            user = user.toUserResponse()
        )
    }

    fun refreshToken(request: RefreshTokenRequest): AuthResponse {
        val userEmail = jwtService.extractUsername(request.refreshToken)
        val userDetails = userDetailsService.loadUserByUsername(userEmail)

        if (!jwtService.isTokenValid(request.refreshToken, userDetails)) {
            throw AuthException("Invalid refresh token")
        }

        val user = userRepository.findByEmail(userEmail)
            .orElseThrow { AuthException("User not found") }

        val accessToken = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtExpiration,
            user = user.toUserResponse()
        )
    }

    @Transactional
    fun forgotPassword(request: ForgotPasswordRequest): MessageResponse {
        val user = userRepository.findByEmail(request.email).orElse(null)
            ?: return MessageResponse("If the email exists, a password reset link will be sent")

        // Generate reset token
        val resetToken = UUID.randomUUID().toString()
        user.passwordResetToken = resetToken
        user.passwordResetTokenExpiry = LocalDateTime.now().plusHours(1)
        user.updatedAt = LocalDateTime.now()

        userRepository.save(user)

        // TODO: Send email with reset token
        // For now, we'll just log it (in production, integrate with email service)
        println("Password reset token for ${user.email}: $resetToken")

        return MessageResponse("If the email exists, a password reset link will be sent")
    }

    @Transactional
    fun resetPassword(request: ResetPasswordRequest): MessageResponse {
        val user = userRepository.findByPasswordResetToken(request.token)
            .orElseThrow { AuthException("Invalid or expired reset token") }

        // Check if token is expired
        if (user.passwordResetTokenExpiry?.isBefore(LocalDateTime.now()) == true) {
            throw AuthException("Reset token has expired")
        }

        // Update password
        user.password = passwordEncoder.encode(request.newPassword)
        user.passwordResetToken = null
        user.passwordResetTokenExpiry = null
        user.updatedAt = LocalDateTime.now()

        userRepository.save(user)

        return MessageResponse("Password has been reset successfully")
    }

    @Transactional
    fun changePassword(email: String, request: ChangePasswordRequest): MessageResponse {
        val user = userRepository.findByEmail(email)
            .orElseThrow { AuthException("User not found") }

        // Verify current password
        if (!passwordEncoder.matches(request.currentPassword, user.password)) {
            throw AuthException("Current password is incorrect")
        }

        // Update password
        user.password = passwordEncoder.encode(request.newPassword)
        user.updatedAt = LocalDateTime.now()

        userRepository.save(user)

        return MessageResponse("Password changed successfully")
    }

    fun getCurrentUser(email: String): UserResponse {
        val user = userRepository.findByEmail(email)
            .orElseThrow { AuthException("User not found") }
        return user.toUserResponse()
    }

    private fun User.toUserResponse() = UserResponse(
        id = this.id,
        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        role = this.role.name
    )
}
