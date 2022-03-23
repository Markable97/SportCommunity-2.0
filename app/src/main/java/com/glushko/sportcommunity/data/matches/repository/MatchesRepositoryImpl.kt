package com.glushko.sportcommunity.data.matches.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.matches.network.ResponseFootballMatches
import com.glushko.sportcommunity.data.matches.network.toModel
import com.glushko.sportcommunity.data.matches.network.toModelCalendar
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = MatchesRepository::class, component = SingletonComponent::class)
class MatchesRepositoryImpl @Inject constructor(private val api: ApiService): MatchesRepository {
    override fun getResults(divisionId: Int): Single<List<MatchFootballDisplayData>> {
        return api.getFootballMatchesDivision(ResponseFootballMatches.createMap(divisionId))
            .subscribeOn(Schedulers.io())
            .map { it.toModel() }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCalendar(divisionId: Int): Single<List<MatchFootballDisplayData>> {
        return api.getFootballMatchesCalendarDivision(ResponseFootballMatches.createMap(divisionId))
            .subscribeOn(Schedulers.io())
            .map { it.toModelCalendar() }
            .observeOn(AndroidSchedulers.mainThread())
    }
}