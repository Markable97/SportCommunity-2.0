package com.glushko.sportcommunity.domain.admin.schedule

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.presentation.admin.schedule.model.CalendarDayUI
import com.glushko.sportcommunity.presentation.admin.schedule.model.ScheduleUI
import com.glushko.sportcommunity.presentation.admin.schedule.model.StadiumUI
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
    suspend fun getAssignMatches(leagueId: Int = 1): Result<MutableList<MatchUI>>
    suspend fun addMatchInSchedule(
        stadiumId: Int,
        gameDate: String,
        matchId: Long,
        leagueId: Int = 1
    ): Result<String>
    suspend fun deleteMatchInSchedule(
        stadiumId: Int,
        gameDate: String,
        leagueId: Int = 1
    ): Result<String>
}