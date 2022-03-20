package com.glushko.sportcommunity.domain.repository.tournament_table

import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import io.reactivex.Single

interface TournamentTableRepository {
    fun getTournamentTable(divisionId: Int, seasonId: Int = 0, teamId: Long = 0) : Single<List<TournamentTableDisplayData>>
}