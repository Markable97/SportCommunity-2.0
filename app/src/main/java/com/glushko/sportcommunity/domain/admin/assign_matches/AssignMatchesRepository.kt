package com.glushko.sportcommunity.domain.admin.assign_matches

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.divisions.model.DivisionUI
import com.glushko.sportcommunity.util.Result

interface AssignMatchesRepository {

    suspend fun getAssignMatches(leagueId: Int = 1): Result<List<MatchUI>>
    suspend fun getDivisions(leagueId: Int = 1): Result<List<DivisionUI>>
    suspend fun getUnassignedTours(tournamentId: Int): Result<List<String>>
    suspend fun getUnassignedMatches(leagueId: Int = 1, tournamentId: Int, tours: String): Result<List<MatchUI>>
    suspend fun addAssignMatch(matches: List<MatchUI>, isDeleting: Boolean = false): Result<String>
}