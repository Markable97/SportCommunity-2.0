package com.glushko.sportcommunity.domain.repository.admin.schedule

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.CalendarDayUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.ScheduleUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.util.Result

interface ScheduleRepository {

    suspend fun getStadiums(leagueId: Int = 1): Result<List<StadiumUI>>
    suspend fun createSchedule(
        date: Long,
        time: Pair<Int, Int>,
        stadiumId: Int,
        countGame: Int,
        halfTime: Int,
        halfBreakTime: Int = 0,
        betweenGameBreakTime: Int = 0,
        leagueId: Int = 1
    ): Result<String>
    fun getCalendar(): List<CalendarDayUI>
    suspend fun getSchedule(unixDate: Long, leagueId: Int = 1): Result<List<ScheduleUI>>
    suspend fun getAssignMatches(leagueId: Int = 1): Result<List<MatchUI>>
}