package com.glushko.sportcommunity.data.admin.schedule.repository


import android.icu.util.Calendar
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.CalendarDayUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.ScheduleUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.toModel
import com.glushko.sportcommunity.data.admin.schedule.stadium.network.ResponseSchedule
import com.glushko.sportcommunity.data.admin.schedule.stadium.network.ResponseStadiums
import com.glushko.sportcommunity.data.admin.schedule.stadium.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  ScheduleRepository::class, component = SingletonComponent::class)
class ScheduleRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val api: ApiService
): ScheduleRepository {
    override suspend fun getStadiums(leagueId: Int): Result<List<StadiumUI>> {
        val response = networkUtils.getResponseResult<ResponseStadiums>(ResponseStadiums::class.java){
            api.getStadiums(leagueId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.stadiums.map { it.toModel() })
        }
    }

    override suspend fun createSchedule(
        date: Long,
        time: Pair<Int, Int>,
        stadiumId: Int,
        countGame: Int,
        halfTime: Int,
        halfBreakTime: Int,
        betweenGameBreakTime: Int,
        leagueId: Int
    ): Result<String> {
        val timeUnix = (time.first*60*60+time.second*60)
        val timeStart = date + timeUnix
        val timeGame = halfTime * 2
        val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java){
            api.createSchedule(
                leagueId = leagueId,
                stadiumId = stadiumId,
                timeStart = timeStart,
                timeGame = timeGame,
                timeBreakBetween = betweenGameBreakTime,
                timeBreakHalf = halfBreakTime,
                countGame = countGame
            )
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.message)
        }
    }

    private fun getNamingDayOfWeek(dayOfWeek: Int): String {
        return when(dayOfWeek){
            1 -> "ВС"
            2 -> "ПН"
            3 -> "ВТ"
            4 -> "СР"
            5 -> "ЧТ"
            6 -> "ПТ"
            else -> "СБ"
        }
    }

    private fun getCalendarDayUI(calendar: Calendar): CalendarDayUI {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val unixTime = calendar.timeInMillis / 1000
        return CalendarDayUI(
            unixDate = unixTime,
            dayOfMonth = dayOfMonth,
            dayOfWeek = getNamingDayOfWeek(dayOfWeek),
            isSelect = true
        )
    }

    override fun getCalendar(): List<CalendarDayUI> {
        val calendar = Calendar.getInstance()
        val days = mutableListOf<CalendarDayUI>()
        days.add(getCalendarDayUI(calendar))
        for (day in 1..14){
            calendar.add(Calendar.DATE, 1)
            days.add(getCalendarDayUI(calendar))
        }
        return days.toMutableList()
    }

    override suspend fun getSchedule(unixDate: Long, leagueId: Int): Result<List<ScheduleUI>> {
        val response = networkUtils.getResponseResult<ResponseSchedule>(ResponseSchedule::class.java){
            api.getSchedule(leagueId, unixDate)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(
                response.data.toModel()
            )
        }
    }

}