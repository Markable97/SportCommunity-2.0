package com.glushko.sportcommunity.data.squad.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.squad.model.SquadPlayer
import com.glushko.sportcommunity.data.squad.model.toModel
import com.glushko.sportcommunity.domain.repository.squad.SquadRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  SquadRepository::class, component = SingletonComponent::class)
class SquadRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val api: ApiService
): SquadRepository{

    override suspend fun getSquad(teamId: Int): Resource<List<SquadPlayer>> {
        val response = networkUtils.getResponse {
            api.getFootballSquad(teamId)
        }
        return if (response is Resource.Success){
            Resource.Success(response.data!!.players.map { it.toModel() })
        } else {
            Resource.Error(error = response.error)
        }
    }
}