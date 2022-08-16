package com.glushko.sportcommunity.domain.repository.match_detail

import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
import com.glushko.sportcommunity.util.Resource
import io.reactivex.Single

interface MatchDetailRepository {
    suspend fun getMatchDetail(matchId: Long): Resource<List<PlayerDisplayData>>
}