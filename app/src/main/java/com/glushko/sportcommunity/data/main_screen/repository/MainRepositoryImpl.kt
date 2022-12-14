package com.glushko.sportcommunity.data.main_screen.repository

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.network.toModel
import com.glushko.sportcommunity.data.main_screen.model.*
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
@BoundTo(supertype =  MainRepository::class, component = SingletonComponent::class)
class MainRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
): MainRepository {

    override var tournamentTable: List<TournamentTableDisplayData> = emptyList()
    override var calendar: List<MatchFootballDisplayData> = emptyList()
    override var results: List<MatchFootballDisplayData> = emptyList()
    override var statistics: PlayersWithStatisticsRes = PlayersWithStatisticsRes()


    override suspend fun getLeagues(): Resource<List<LeaguesDisplayData>> {
        val response = networkUtils.getResponse {
            api.getFootballLeagues()
        }
        return if (response is Resource.Success) {
            Resource.Success(response.data!!.toModel())
        } else {
            Resource.Error(error = response.error)
        }
    }

    override suspend fun getMainScreen(divisionId: Int): Resource<ResponseMainScreen> {
        val response = networkUtils.getResponse {
            api.getMainScreen(ResponseMainScreen.createMap(divisionId))
        }
        if (response is Resource.Success){
            tournamentTable = response.data!!.toTournamentTable()
            calendar = response.data.toCalendar()
            results = response.data.toResults()
            statistics = response.data.statistics
        }
        return response
    }
}