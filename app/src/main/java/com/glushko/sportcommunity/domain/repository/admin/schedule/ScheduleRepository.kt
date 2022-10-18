package com.glushko.sportcommunity.domain.repository.admin.schedule

import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.util.Result

interface ScheduleRepository {

    suspend fun getStadiums(leagueId: Int = 1): Result<List<StadiumUI>>
}