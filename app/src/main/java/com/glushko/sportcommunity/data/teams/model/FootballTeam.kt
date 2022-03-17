package com.glushko.sportcommunity.data.teams.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FootballTeam(val team_id: Int,
                        val team_name: String): Parcelable
