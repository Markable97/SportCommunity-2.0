package com.glushko.sportcommunity.domain.tournament

import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.presentation.tournament.model.*
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result

interface TournamentRepository {
    fun getTournamentInfo() : TournamentInfoDisplayData
    fun getTournamentTable() : List<TournamentTableDisplayData>
    fun getStatistics(): List<PlayerStatisticAdapter>
    fun getMedia(): List<MediaUI>
    suspend fun getStatisticsType(type: TypeStatistics): Resource<List<PlayerStatisticDisplayData>>
    fun getTournamentTableTeam(teamId: Int): List<TournamentTableDisplayData>
    suspend fun getTournamentTableForTeam(teamId: Int): Result<List<TournamentTableDisplayData>>
}