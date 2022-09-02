package com.glushko.sportcommunity.data.squad.repository

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.squad.model.SquadPlayer
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
//        val response = networkUtils.getResponse {
//
//        }
        return Resource.Success(getSamples())
    }

    private fun getSamples() = listOf(
        SquadPlayer(
            playerId = 0,
            playerName = "Глушко 1",
            amplua = "Вратарь",
            avatarUrl = ""
        ),
        SquadPlayer(
            playerId = 0,
            playerName = "Глушко 12",
            amplua = "Защитник",
            avatarUrl = ""
        ),
        SquadPlayer(
            playerId = 0,
            playerName = "Глушко 123",
            amplua = "Полузащитник",
            avatarUrl = ""
        ),
        SquadPlayer(
            playerId = 0,
            playerName = "Глушко 1234",
            amplua = "Нападающий",
            avatarUrl = ""
        ),
        SquadPlayer(
            playerId = 0,
            playerName = "Глушко 12345",
            amplua = "Нападающий",
            avatarUrl = ""
        ),
    )
}