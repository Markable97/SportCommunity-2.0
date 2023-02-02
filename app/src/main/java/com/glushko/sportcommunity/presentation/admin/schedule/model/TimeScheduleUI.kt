package com.glushko.sportcommunity.presentation.admin.schedule.model

import android.os.Parcelable
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeScheduleUI(
    val time: String,
    val date: String,
    var match: MatchUI?
): Parcelable
