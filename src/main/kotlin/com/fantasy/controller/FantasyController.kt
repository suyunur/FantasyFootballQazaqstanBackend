package com.fantasy.controller

import com.fantasy.dto.FantasyInfoResponse
import com.fantasy.service.FantasyService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/fantasy")
class FantasyController(
    private val fantasyService: FantasyService
) {

    @GetMapping("/info")
    fun getInfo(authentication: Authentication): ResponseEntity<FantasyInfoResponse> {
        val response = fantasyService.getInfo(authentication.name)
        return ResponseEntity.ok(response)
    }
}