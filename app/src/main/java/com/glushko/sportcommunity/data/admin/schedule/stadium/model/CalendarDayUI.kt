package com.glushko.sportcommunity.data.admin.schedule.stadium.model

data class CalendarDayUI(
    val unixTime: Long,
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val isSelect: Boolean = false,
)