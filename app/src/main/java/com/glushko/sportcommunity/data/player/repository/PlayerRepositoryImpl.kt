package com.glushko.sportcommunity.data.player.repository

import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.player.network.ResponseProfilePlayer
import com.glushko.sportcommunity.domain.player.PlayerRepository
import com.glushko.sportcommunity.presentation.player.model.ProfilePlayerUI
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = PlayerRepository::class, component = SingletonComponent::class)
class PlayerRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils
) : PlayerRepository {
    override suspend fun getPlayerInfo(playerId: Int): Result<ProfilePlayerUI> {
        val response = networkUtils.getResponseResult<ResponseProfilePlayer>(ResponseProfilePlayer::class.java) {
            apiService.getPlayerInfo(playerId)
        }
        return when(response) {
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModelUI())
        }
    }


}