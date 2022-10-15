package com.glushko.sportcommunity.domain.repository.admin.assign_matches

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.util.Result

interface AssignMatchesRepository {

    suspend fun getAssignMatches(leagueId: Int = 1): Result<List<MatchUI>>

}