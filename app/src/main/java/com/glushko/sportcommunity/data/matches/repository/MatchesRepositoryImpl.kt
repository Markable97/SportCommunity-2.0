package com.glushko.sportcommunity.data.matches.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.matches.network.ResponseFootballMatches
import com.glushko.sportcommunity.data.matches.network.toModel
import com.glushko.sportcommunity.data.matches.network.toModelCalendar
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = MatchesRepository::class, component = SingletonComponent::class)
class MatchesRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
    ): MatchesRepository {
    override suspend fun getResults(divisionId: Int): Resource<List<MatchFootballDisplayData>> {
        val response = networkUtils.getResponse {
            api.getFootballMatchesDivision(ResponseFootballMatches.createMap(divisionId))
        }
        return if (response is Resource.Success){
            Resource.Success(response.data!!.toModel())
        }else {
            Resource.Error(error = response.error)
        }
    }

    override suspend fun getCalendar(divisionId: Int): Resource<List<MatchFootballDisplayData>> {
        val response = networkUtils.getResponse {
            api.getFootballMatchesCalendarDivision(ResponseFootballMatches.createMap(divisionId))
        }
        return if (response is Resource.Success){
            Resource.Success(response.data!!.toModelCalendar())
        }else {
            Resource.Error(error = response.error)
        }
    }
}