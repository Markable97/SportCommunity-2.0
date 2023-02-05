package com.glushko.sportcommunity.data.matches.repository

import com.glushko.sportcommunity.data.admin.schedule.stadium.network.ResponseSchedule
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.main_screen.MainRepository
import com.glushko.sportcommunity.domain.matches.MatchesRepository
import com.glushko.sportcommunity.util.NetworkUtils
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import com.glushko.sportcommunity.util.Result

@BoundTo(supertype = MatchesRepository::class, component = SingletonComponent::class)
class MatchesRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils,
    private val mainRepository: MainRepository
    ): MatchesRepository {
    override fun getResults(): List<MatchFootballDisplayData> {
        return mainRepository.results
    }

    override fun getCalendar(): List<MatchFootballDisplayData> {
        return mainRepository.calendar
    }

    override suspend fun getMatches(teamId: Int?, tournamentId: Int?): Result<List<MatchFootballDisplayData>> {
        val response = networkUtils.getResponseResult<ResponseSchedule>(ResponseSchedule::class.java) {
            api.getMatches(teamId, tournamentId)
        }
        return when(response) {
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.schedule.map { it.toModelMatches() })
        }
    }
}