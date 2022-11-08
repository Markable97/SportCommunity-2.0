package com.glushko.sportcommunity.domain.repository.admin.edit_match

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.util.Result

interface EditMatchRepository {
    suspend fun getActions(): Result<List<ActionUI>>
    suspend fun getAssignMatches(leagueId: Int = 1): Result<List<MatchUI>>
    suspend fun getPlayersForMatch(teamHome: Int, teamGuest: Int): Result<List<PLayerUI>>
}