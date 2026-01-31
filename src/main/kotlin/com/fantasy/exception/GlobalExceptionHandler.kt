package com.fantasy.exception

import com.fantasy.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AuthException::class)
    fun handleAuthException(ex: AuthException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(message = ex.message ?: "Authentication error"))
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(message = "Invalid email or password"))
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(ex: UsernameNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = ex.message ?: "User not found"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                message = "Validation failed",
                errors = errors
            ))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(message = "An unexpected error occurred"))
    }
}
