package com.glushko.sportcommunity.presentation.admin.schedule.model

data class ScheduleUI(
    val stadium: StadiumUI,
    val times: MutableList<TimeScheduleUI>,
    var isOpen: Boolean = false
)
