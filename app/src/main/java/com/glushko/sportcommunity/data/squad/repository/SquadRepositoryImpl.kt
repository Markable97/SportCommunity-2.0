package com.glushko.sportcommunity.data.squad.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.statistics.network.*
import com.glushko.sportcommunity.domain.repository.squad.SquadRepository
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(supertype =  SquadRepository::class, component = SingletonComponent::class)
class SquadRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val api: ApiService
): SquadRepository{

    private var squadStatistics = emptyList<PlayerWithStatisticsRes>()
    private var squad = emptyList<SquadPlayerUI>()

    override suspend fun getSquadInfo(teamId: Int): Result<ResponseFootballSquad> {
        val response = networkUtils.getResponseResult<ResponseFootballSquad>(ResponseFootballSquad::class.java) {
            api.getFootballSquad(teamId)
        }
        if (response is Result.Success){
            squadStatistics = response.data.squad
            squad = response.data.squad.distinctBy { it.playerId }.map { it.toModelSquad() }
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> {Result.Success(response.data)}
        }
//        return if (response is Resource.Success){
//            squadStatistics = response.data!!.statistics
//            squad = squadStatistics.toModelSquad()
//            Resource.Success(response.data)
//        } else {
//            Resource.Error(error = response.error)
//        }
    }

    override fun getSquad(): List<SquadPlayerUI> {
        return squad
    }

    override suspend fun getSquadStatistics(): List<PlayerStatisticAdapter> {
        return toModelWidget()
    }

    private fun toModelWidget(): List<PlayerStatisticAdapter> {
        val goals = mutableListOf<PlayerStatisticDisplayData>()
        val assists = mutableListOf<PlayerStatisticDisplayData>()
        val yellowCards = mutableListOf<PlayerStatisticDisplayData>()
        val redCards = mutableListOf<PlayerStatisticDisplayData>()
        squadStatistics.forEach { player ->
            when(player.actionId){
                in Constants.TYPE_ACTION_GOALS -> goals.add(player.toModel())
                Constants.TYPE_ACTION_ASSIST -> assists.add(player.toModel())
                Constants.TYPE_ACTION_YELLOW_CARD -> yellowCards.add(player.toModel())
                Constants.TYPE_ACTION_RED_CARD -> redCards.add(player.toModel())
            }
        }
        return listOf(
            PlayerStatisticAdapter(
                TypeStatistics.GOALS,
                firstPlayer = goals.getOrNull(0),
                secondPlayer = goals.getOrNull(1),
                thirdPlayer = goals.getOrNull(2)
            ),
            PlayerStatisticAdapter(
                TypeStatistics.ASSISTS,
                firstPlayer = assists.getOrNull(0),
                secondPlayer = assists.getOrNull(1),
                thirdPlayer = assists.getOrNull(2)
            ),
            PlayerStatisticAdapter(
                TypeStatistics.YELLOW_CARDS,
                firstPlayer = yellowCards.getOrNull(0),
                secondPlayer = yellowCards.getOrNull(1),
                thirdPlayer = yellowCards.getOrNull(2)
            ),
            PlayerStatisticAdapter(
                TypeStatistics.RED_CARDS,
                firstPlayer = redCards.getOrNull(0),
                secondPlayer = redCards.getOrNull(1),
                thirdPlayer = redCards.getOrNull(2)
            )
        )
    }
    override suspend fun getSquadStatisticsAll(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>> {
        return Resource.Success(
            when (type) {
                TypeStatistics.GOALS -> squadStatistics.filter { it.actionId in Constants.TYPE_ACTION_GOALS }.map { it.toModel() }
                TypeStatistics.ASSISTS -> squadStatistics.filter { it.actionId == Constants.TYPE_ACTION_ASSIST }.map { it.toModel() }
                TypeStatistics.YELLOW_CARDS -> squadStatistics.filter { it.actionId == Constants.TYPE_ACTION_YELLOW_CARD }.map { it.toModel() }
                TypeStatistics.RED_CARDS -> squadStatistics.filter { it.actionId == Constants.TYPE_ACTION_RED_CARD }.map { it.toModel() }
            }
        )
    }
}