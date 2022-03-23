package com.glushko.sportcommunity.domain.repository.matches

import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import io.reactivex.Single

interface MatchesRepository {
    fun getResults(divisionId: Int): Single<List<MatchFootballDisplayData>>

    fun getCalendar(divisionId: Int): Single<List<MatchFootballDisplayData>>
}