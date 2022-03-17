package com.glushko.sportcommunity.data.division.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FootballDivision(
    val division_id: Int,
    val division_name: String
) : Parcelable
