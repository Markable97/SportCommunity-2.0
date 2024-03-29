package com.glushko.sportcommunity.data.admin.schedule.network

import com.glushko.sportcommunity.data.admin.schedule.model.Schedule
import com.glushko.sportcommunity.presentation.admin.schedule.model.TimeScheduleUI
import com.glushko.sportcommunity.data.admin.schedule.model.toModel
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.presentation.admin.schedule.model.ScheduleUI
import com.google.gson.annotations.SerializedName
import timber.log.Timber

class ResponseSchedule(
    success: Int,
    message: String,
    @SerializedName("schedule", alternate = ["matches"])
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