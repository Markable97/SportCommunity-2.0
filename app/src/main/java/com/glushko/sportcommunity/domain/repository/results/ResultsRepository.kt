package com.glushko.sportcommunity.domain.repository.results

import com.glushko.sportcommunity.data.results.model.MatchFootballDisplayData
import io.reactivex.Single

interface ResultsRepository {
    fun getResults(divisionId: Int): Single<List<MatchFootballDisplayData>>
}