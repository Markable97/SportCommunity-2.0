package com.glushko.sportcommunity.data.admin.schedule.stadium.network

import com.glushko.sportcommunity.data.admin.assign_matches.model.toModel
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.Schedule
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.ScheduleUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.TimeScheduleUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.toModel
import com.glushko.sportcommunity.data.network.BaseResponse
import timber.log.Timber

class ResponseSchedule(
    success: Int,
    message: String,
    val schedule: List<Schedule> = emptyList()
): BaseResponse(success, message)

fun ResponseSchedule.toModel(): List<ScheduleUI>{
    val scheduleList = schedule.groupBy({it.stadium.toModel()}){
        val dateTime = it.gameDate.split(" ")
        TimeScheduleUI(
                date = dateTime.first(),
                time = dateTime.last(),
                match = it.match?.toModel()
        )
    }.map {
        ScheduleUI(
            it.key,
            it.value.toMutableList()
        )
    }
    Timber.d("$scheduleList")
    return scheduleList
}

//if (scheduleList.isNotEmpty()){
//    var stadiumPrev = schedule.first().stadium
//    val timeScheduleUI = mutableListOf<TimeScheduleUI>()
//    schedule.forEach {
//        if (stadiumPrev.stadiumId == it.stadium.stadiumId){
//            val dateTime = it.gameDate.split(" ")
//            timeScheduleUI.add(
//                TimeScheduleUI(
//                    date = dateTime.first(),
//                    time = dateTime.last(),
//                    match = it.match?.toModel()
//                )
//            )
//        } else {
//            scheduleList.add(
//                ScheduleUI(
//                    stadium = stadiumPrev.toModel(),
//                    times = timeScheduleUI.toList()
//                )
//            )
//            stadiumPrev = it.stadium
//        }
//    }
//    scheduleList.add(
//        ScheduleUI(
//            stadium = stadiumPrev.toModel(),
//            times = timeScheduleUI.toList()
//        )
//    )
//}