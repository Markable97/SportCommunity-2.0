package com.glushko.sportcommunity.presentation.admin.schedule.model

data class CalendarDayUI(
    val unixDate: Long,
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val isSelect: Boolean = false,
)