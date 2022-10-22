package com.glushko.sportcommunity.data.admin.schedule.stadium.model

data class CalendarDayUI(
    val unixDate: Long,
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val isSelect: Boolean = false,
)