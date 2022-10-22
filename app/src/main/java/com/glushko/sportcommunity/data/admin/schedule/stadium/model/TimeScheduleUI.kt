package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import android.os.Parcelable
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeScheduleUI(
    val time: String,
    val date: String,
    val match: MatchUI?
): Parcelable
