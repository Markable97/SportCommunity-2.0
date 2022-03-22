package com.glushko.sportcommunity.data.results.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchFootballDisplayData(
    val matchId: Long,
    val leagueName: String,
    val divisionName: String,
    val tour: Int,
    val teamHomeName : String,
    val teamHomeGoal: Int,
    val teamGuestName: String,
    val teamGuestGoal: Int
): Parcelable