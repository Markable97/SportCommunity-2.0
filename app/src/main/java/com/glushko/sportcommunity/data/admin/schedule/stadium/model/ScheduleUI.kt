package com.glushko.sportcommunity.data.admin.schedule.stadium.model

data class ScheduleUI(
    val stadium: StadiumUI,
    val times: List<TimeScheduleUI>,
    var isOpen: Boolean = false
)