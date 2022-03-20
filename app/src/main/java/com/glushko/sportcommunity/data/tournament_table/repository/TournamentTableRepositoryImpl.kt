package com.glushko.sportcommunity.data.tournament_table.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import com.glushko.sportcommunity.data.tournament_table.network.ResponseTournamentTableFootball
import com.glushko.sportcommunity.data.tournament_table.network.toModel
import com.glushko.sportcommunity.domain.repository.tournament_table.TournamentTableRepository
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  TournamentTableRepository::class, component = SingletonComponent::class)
class TournamentTableRepositoryImpl @Inject constructor(private val api: ApiService): TournamentTableRepository {
    override fun getTournamentTable(
        divisionId: Int,
        seasonId: Int,
        teamId: Long
    ): Single<List<TournamentTableDisplayData>> {
        return api.getTournamentTableFootball(ResponseTournamentTableFootball.createMap(division_id = divisionId, season_id = 0, team_id = 0))
            .subscribeOn(Schedulers.io())
            .map { it.toModel() }
            .observeOn(AndroidSchedulers.mainThread())
    }
}