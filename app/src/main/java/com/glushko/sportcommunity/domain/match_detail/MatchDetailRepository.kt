package com.glushko.sportcommunity.domain.match_detail

import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.util.Result

interface MatchDetailRepository {
    suspend fun getMatchDetail(matchId: Long, teamHomeId: Int): Result<List<PlayerInMatchSegment>>
}