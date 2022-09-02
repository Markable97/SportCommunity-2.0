package com.glushko.sportcommunity.domain.repository.squad

import com.glushko.sportcommunity.data.squad.model.SquadPlayer
import com.glushko.sportcommunity.util.Resource

interface SquadRepository {

    suspend fun getSquad(teamId: Int): Resource<List<SquadPlayer>>

}