package com.glushko.sportcommunity.domain.matches

import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData

interface MatchesRepository {
    fun getResults(): List<MatchFootballDisplayData>

    fun getCalendar(): List<MatchFootballDisplayData>
}