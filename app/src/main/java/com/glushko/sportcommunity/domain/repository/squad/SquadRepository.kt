package com.glushko.sportcommunity.domain.repository.squad

import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.util.Resource

interface SquadRepository {

    suspend fun getSquadInfo(teamId: Int): Resource<ResponseFootballSquad>
    fun getSquad(): List<SquadPlayerUI>
    suspend fun getSquadStatistics(): List<PlayerStatisticAdapter>
    suspend fun getSquadStatisticsAll(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>>
}