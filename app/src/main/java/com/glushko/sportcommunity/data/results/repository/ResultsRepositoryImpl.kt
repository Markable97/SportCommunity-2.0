package com.glushko.sportcommunity.data.results.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.results.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.results.network.ResponseFootballMatches
import com.glushko.sportcommunity.data.results.network.toModel
import com.glushko.sportcommunity.domain.repository.results.ResultsRepository
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = ResultsRepository::class, component = SingletonComponent::class)
class ResultsRepositoryImpl @Inject constructor(private val api: ApiService): ResultsRepository {
    override fun getResults(divisionId: Int): Single<List<MatchFootballDisplayData>> {
        return api.getFootballMatchesDivision(ResponseFootballMatches.createMap(divisionId))
            .subscribeOn(Schedulers.io())
            .map { it.toModel() }
            .observeOn(AndroidSchedulers.mainThread())
    }
}