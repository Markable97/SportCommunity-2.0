package com.glushko.sportcommunity.data.tournament_table.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import com.glushko.sportcommunity.data.tournament_table.network.ResponseTournamentTableFootball
import com.glushko.sportcommunity.data.tournament_table.network.toModel
import com.glushko.sportcommunity.domain.repository.tournament_table.TournamentTableRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  TournamentTableRepository::class, component = SingletonComponent::class)
class TournamentTableRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
    ): TournamentTableRepository {
    override suspend fun getTournamentTable(
        divisionId: Int,
        seasonId: Int,
        teamId: Long
    ): Resource<List<TournamentTableDisplayData>> {
        val response = networkUtils.getResponse {
            api.getTournamentTableFootball(ResponseTournamentTableFootball.createMap(division_id = divisionId, season_id = 0, team_id = 0))
        }

        return if (response is Resource.Success){
            Resource.Success(response.data!!.toModel())
        } else {
            Resource.Error(error = response.error)
        }
    }
}