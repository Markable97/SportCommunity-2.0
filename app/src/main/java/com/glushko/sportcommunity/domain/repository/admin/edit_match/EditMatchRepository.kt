package com.glushko.sportcommunity.domain.repository.admin.edit_match

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.util.Result

interface EditMatchRepository {
    suspend fun getActions(): Result<List<ActionUI>>
    suspend fun getAssignMatches(leagueId: Int = 1): Result<List<MatchUI>>
    suspend fun getPlayersForMatch(teamHome: Int, teamGuest: Int): Result<List<PLayerUI>>
    suspend fun addPlayerInMatch(matchId: Long, players: List<PLayerUI>): Result<String>
    suspend fun addScore(match: MatchUI): Result<String>
    suspend fun addPlayerAction(matchId: Long, isAdd: Boolean, playerWithAction: PlayerWithActionUI): Result<String>
    suspend fun deletePlayersWithGoals(matchId: Long, playersWithGoals: List<PlayerWithActionUI>): Result<String>
}