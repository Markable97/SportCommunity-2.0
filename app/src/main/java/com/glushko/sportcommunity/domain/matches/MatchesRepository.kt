package com.glushko.sportcommunity.domain.matches


import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.util.Result
interface MatchesRepository {
    fun getResults(): List<MatchFootballDisplayData>

    fun getCalendar(): List<MatchFootballDisplayData>

    suspend fun getMatches(teamId: Int?, tournamentId: Int?): Result<List<MatchFootballDisplayData>>
}