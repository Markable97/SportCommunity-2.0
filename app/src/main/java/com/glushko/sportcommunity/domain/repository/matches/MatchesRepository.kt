package com.glushko.sportcommunity.domain.repository.matches

import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.util.Resource
import io.reactivex.Single

interface MatchesRepository {
    suspend fun getResults(divisionId: Int): Resource<List<MatchFootballDisplayData>>

    suspend fun getCalendar(divisionId: Int): Resource<List<MatchFootballDisplayData>>
}