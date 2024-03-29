package com.glushko.sportcommunity.domain.admin.edit_match

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.util.Result

interface EditMatchRepository {
    suspend fun getActions(): Result<List<ActionUI>>
    suspend fun getAssignMatches(matchId: Long?, leagueId: Int = 1): Result<List<MatchUI>>
    suspend fun getPlayersForMatch(teamHome: Int, teamGuest: Int, matchId: Long, fromMatchDetail: Boolean): Result<Pair<List<PLayerUI>, List<PlayerWithActionUI>>>
    suspend fun addPlayersInMatch(matchId: Long, players: List<PLayerUI>): Result<String>
    suspend fun deletePlayersInMatch(matchId: Long, players: List<PLayerUI>): Result<String>
    suspend fun addScore(match: MatchUI): Result<String>
    suspend fun addPlayerAction(matchId: Long, isAdd: Boolean, playerWithAction: PlayerWithActionUI): Result<String>
    suspend fun deletePlayersWithGoals(matchId: Long, playersWithGoals: List<PlayerWithActionUI>): Result<String>
}