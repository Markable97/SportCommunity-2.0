package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI

data class TimeScheduleUI(
    val time: String,
    val date: String,
    val match: MatchUI?
)
