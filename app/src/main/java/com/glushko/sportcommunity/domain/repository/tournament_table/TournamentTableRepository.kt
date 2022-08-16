package com.glushko.sportcommunity.domain.repository.tournament_table

import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import com.glushko.sportcommunity.util.Resource
import io.reactivex.Single

interface TournamentTableRepository {
    suspend fun getTournamentTable(divisionId: Int, seasonId: Int = 0, teamId: Long = 0) : Resource<List<TournamentTableDisplayData>>
}