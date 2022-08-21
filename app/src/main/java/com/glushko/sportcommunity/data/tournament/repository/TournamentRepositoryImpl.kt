package com.glushko.sportcommunity.data.tournament.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.data.tournament.network.ResponseTournamentTableFootball
import com.glushko.sportcommunity.data.tournament.network.toModel
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
@BoundTo(supertype =  TournamentRepository::class, component = SingletonComponent::class)
class TournamentRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
    ): TournamentRepository {

    companion object {
        const val COUNT_FOR_TABLE = 4
    }

    private var tournamentTable = listOf<TournamentTableDisplayData>()

    override suspend fun getTournamentTable(
        divisionId: Int,
        seasonId: Int,
        teamId: Long
    ): Resource<List<TournamentTableDisplayData>> {
        val response = networkUtils.getResponse {
            api.getTournamentTableFootball(ResponseTournamentTableFootball.createMap(division_id = divisionId, season_id = 0, team_id = 0))
        }

        return if (response is Resource.Success){
            tournamentTable = response.data!!.toModel()
            Resource.Success(response.data.toModel())
        } else {
            Resource.Error(error = response.error)
        }
    }

    override fun getStatistics(divisionId: Int): Resource<List<PlayerStatisticAdapter>> {
        return Resource.Success(getSamples())
    }

    override suspend fun getStatisticsType(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>> {
        return Resource.Success(getSamplesForStatisticsScreen())
    }

    override suspend fun getTournamentTableTeam(teamId: Int): Resource<List<TournamentTableDisplayData>>? {
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

    override suspend fun getStatisticsTeam(teamId: Int): Resource<List<PlayerStatisticAdapter>> {
        return Resource.Success(getSamples())
    }

    private fun getSamplesForStatisticsScreen() = listOf(
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer(),
        getPlayer()
    ).sortedByDescending { it.points }

    private fun getSamples() = listOf (
            getForAdapter(TypeStatistics.GOALS),
            getForAdapter(TypeStatistics.ASSISTS),
            getForAdapter(TypeStatistics.YELLOW_CARDS),
            getForAdapter(TypeStatistics.RED_CARDS),
        )

    private fun getForAdapter(typeStatistics: TypeStatistics): PlayerStatisticAdapter{
        val actionsPlayer = listOf(
            getPlayer(),
            getPlayer(),
            getPlayer()
        ).sortedByDescending { it.points }
        return PlayerStatisticAdapter(
            typeStatistics = typeStatistics,
            firstPlayer = actionsPlayer[0],
            secondPlayer = actionsPlayer[1],
            thirdPlayer = actionsPlayer[2]
        )
    }

    private fun getPlayer() = PlayerStatisticDisplayData(
        playerId = Random.nextInt(),
        playerName = "Глушко ${Random.nextInt(0, 10)}",
        playerTeam = "Команда ${Random.nextInt(1, 7)}",
        points = Random.nextInt(0, 25)
    )
}