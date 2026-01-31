package com.fantasy.security

import com.fantasy.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found with email: $username") }

        return User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(listOf(SimpleGrantedAuthority("ROLE_${user.role.name}")))
            .accountExpired(!user.isAccountNonExpired)
            .accountLocked(!user.isAccountNonLocked)
            .credentialsExpired(!user.isCredentialsNonExpired)
            .disabled(!user.isEnabled)
            .build()
    }
}
