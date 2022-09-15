package com.glushko.sportcommunity.data.tournament.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.statistics.network.*
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
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

    override fun getTournamentTable(): List<TournamentTableDisplayData> {
        return mainRepository.tournamentTable
    }

    override fun getStatistics(): List<PlayerStatisticAdapter>{
        return mainRepository.statistics.toModelWidget()
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

    override suspend fun getTournamentTableTeam(teamId: Int): Resource<List<TournamentTableDisplayData>>? {
        val tournamentTable = mainRepository.tournamentTable
        val team = tournamentTable.find { it.teamId == teamId }
        val indexTeam = tournamentTable.indexOf(team)
        return when {
            indexTeam < COUNT_FOR_TABLE -> {
               Resource.Success( tournamentTable.take(COUNT_FOR_TABLE))
            }
            tournamentTable.size - COUNT_FOR_TABLE < indexTeam -> {
                Resource.Success( tournamentTable.takeLast(COUNT_FOR_TABLE))
            }
            else -> {
                val newList = mutableListOf<TournamentTableDisplayData>()
                for (position in indexTeam - 1 until indexTeam + COUNT_FOR_TABLE){
                    newList.add(tournamentTable[position])
                }
                Resource.Success(newList)
            }
        }
    }
}