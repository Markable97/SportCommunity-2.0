package com.glushko.sportcommunity.domain.repository.matches

import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.util.Resource
import io.reactivex.Single

interface MatchesRepository {
    fun getResults(): List<MatchFootballDisplayData>

    fun getCalendar(): List<MatchFootballDisplayData>
}