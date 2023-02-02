package com.glushko.sportcommunity.domain.squad

import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.presentation.tournament.model.TypeStatistics
import com.glushko.sportcommunity.util.Resource

interface SquadRepository {

    suspend fun getSquadInfo(teamId: Int): com.glushko.sportcommunity.util.Result<ResponseFootballSquad>
    fun getSquad(): List<SquadPlayerUI>
    suspend fun getSquadStatistics(): List<PlayerStatisticAdapter>
    suspend fun getSquadStatisticsAll(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>>
}