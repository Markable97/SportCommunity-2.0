package com.glushko.sportcommunity.domain.repository.tournament

import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.util.Resource

interface TournamentRepository {
    fun getTournamentTable() : List<TournamentTableDisplayData>
    fun getStatistics(): List<PlayerStatisticAdapter>
    fun getMedia(): List<MediaUI>
    suspend fun getStatisticsType(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>>
    suspend fun getTournamentTableTeam(teamId: Int): Resource<List<TournamentTableDisplayData>>?
}