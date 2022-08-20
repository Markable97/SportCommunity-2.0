package com.glushko.sportcommunity.domain.repository.tournament

import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.util.Resource

interface TournamentRepository {
    suspend fun getTournamentTable(divisionId: Int, seasonId: Int = 0, teamId: Long = 0) : Resource<List<TournamentTableDisplayData>>
    fun getStatistics(divisionId: Int): Resource<List<PlayerStatisticAdapter>>
    suspend fun getStatisticsTeam(teamId: Int): Resource<List<PlayerStatisticAdapter>>
    suspend fun getTournamentTableTeam(teamId: Int): Resource<List<TournamentTableDisplayData>>?
}