package com.glushko.sportcommunity.data.matches.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.main_screen.MainRepository
import com.glushko.sportcommunity.domain.matches.MatchesRepository
import com.glushko.sportcommunity.util.NetworkUtils
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

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
}