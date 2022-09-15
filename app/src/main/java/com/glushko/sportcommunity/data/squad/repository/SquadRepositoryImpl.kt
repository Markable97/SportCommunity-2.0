package com.glushko.sportcommunity.data.squad.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.statistics.network.*
import com.glushko.sportcommunity.domain.repository.squad.SquadRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
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

    private var squadStatistics = PlayersWithStatisticsRes()
    private var squad = emptyList<SquadPlayerUI>()

    override suspend fun getSquadInfo(teamId: Int): Resource<ResponseFootballSquad> {
        val response = networkUtils.getResponse {
            api.getFootballSquad(teamId)
        }
        return if (response is Resource.Success){
            squadStatistics = response.data!!.statistics
            squad = squadStatistics.toModelSquad()
            Resource.Success(response.data)
        } else {
            Resource.Error(error = response.error)
        }
    }

    override fun getSquad(): List<SquadPlayerUI> {
        return squad
    }

    override suspend fun getSquadStatistics(): List<PlayerStatisticAdapter> {
        return squadStatistics.toModelWidget()
    }

    override suspend fun getSquadStatisticsAll(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>> {
        return Resource.Success(
            when (type) {
                TypeStatistics.GOALS -> squadStatistics.toModelGoals()
                TypeStatistics.ASSISTS -> squadStatistics.toModelAssists()
                TypeStatistics.YELLOW_CARDS -> squadStatistics.toModelYellowCards()
                TypeStatistics.RED_CARDS -> squadStatistics.toModelRedCards()
            }
        )
    }
}