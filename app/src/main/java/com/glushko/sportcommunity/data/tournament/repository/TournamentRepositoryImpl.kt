package com.glushko.sportcommunity.data.tournament.repository

import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.statistics.network.*
import com.glushko.sportcommunity.data.tournament.helper.TournamentTableHelper
import com.glushko.sportcommunity.data.tournament.network.ResponseTournamentTableFootball
import com.glushko.sportcommunity.domain.main_screen.MainRepository
import com.glushko.sportcommunity.domain.tournament.TournamentRepository
import com.glushko.sportcommunity.presentation.tournament.model.*
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  TournamentRepository::class, component = SingletonComponent::class)
class TournamentRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils,
    private val mainRepository: MainRepository
    ): TournamentRepository {

    companion object {
        const val COUNT_FOR_TABLE = 4
    }

    override fun getTournamentInfo(): TournamentInfoDisplayData {
        return mainRepository.tournamentInfo
    }

    override fun getTournamentTable(): List<TournamentTableDisplayData> {
        return mainRepository.tournamentInfo.tournamentTable
    }

    override fun getStatistics(): List<PlayerStatisticAdapter>{
        return mainRepository.statistics.toModelWidget()
    }

    override fun getMedia(): List<MediaUI> {
        return mainRepository.media
    }

    override suspend fun getStatisticsType(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>> {
        val list = when(type){
            TypeStatistics.GOALS ->mainRepository.statistics.toModelGoals()
             TypeStatistics.ASSISTS -> mainRepository.statistics.toModelAssists()
            TypeStatistics.YELLOW_CARDS -> mainRepository.statistics.toModelYellowCards()
            TypeStatistics.RED_CARDS -> mainRepository.statistics.toModelRedCards()
        }
        return Resource.Success(list)
    }

    override fun getTournamentTableTeam(teamId: Int): List<TournamentTableDisplayData> {
        val tournamentTable = mainRepository.tournamentInfo.tournamentTable
        return TournamentTableHelper.getTournamentTableWithPositionTeam(
            table = tournamentTable,
            teamId = teamId
        )
    }

    override suspend fun getTournamentTableForTeam(teamId: Int): Result<List<TournamentTableDisplayData>> {
        val response = networkUtils.getResponseResult<ResponseTournamentTableFootball>(ResponseTournamentTableFootball::class.java){
            api.getTournamentTableTeam(teamId)
        }
        return when(response) {
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(
                TournamentTableHelper.getTournamentTableWithPositionTeam(
                    table = response.data.toModel(),
                    teamId = teamId
                )
            )
        }
    }
}