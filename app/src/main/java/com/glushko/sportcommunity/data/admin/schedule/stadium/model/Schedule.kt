package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import com.glushko.sportcommunity.data.admin.assign_matches.model.Match
import com.google.gson.annotations.SerializedName

data class Schedule(
    val stadium: Stadium,
    @SerializedName("game_date")
    val gameDate: String,
    val match: Match?
)

data class ScheduleUI(
    val stadium: StadiumUI,
    val times: List<TimeScheduleUI>,
    var isOpen: Boolean = false
)