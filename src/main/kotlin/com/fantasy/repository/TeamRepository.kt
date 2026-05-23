package com.fantasy.repository

import com.fantasy.model.Team
import org.springframework.data.repository.Repository
import org.springframework.stereotype.Repository as SpringRepository
import java.util.Optional

@SpringRepository
interface TeamRepository : Repository<Team, Long> {

    fun findById(id: Long): Optional<Team>

    fun save(team: Team): Team
}
