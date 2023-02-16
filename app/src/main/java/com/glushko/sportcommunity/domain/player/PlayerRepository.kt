package com.glushko.sportcommunity.domain.player

import com.glushko.sportcommunity.util.Result

interface PlayerRepository {

    suspend fun getPlayerInfo(playerId: Int): Result<Unit>

}