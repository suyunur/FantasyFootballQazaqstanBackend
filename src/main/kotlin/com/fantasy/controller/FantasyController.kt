package com.fantasy.controller

import com.fantasy.dto.FantasyInfoResponseimport com.fantasy.service.FantasyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/fantasy")
class FantasyController(
    private val fantasyService: FantasyService
) {

    @GetMapping("/info")
    fun getInfo(): ResponseEntity<FantasyInfoResponse> {
        val response = fantasyService.getInfo()
    }
}