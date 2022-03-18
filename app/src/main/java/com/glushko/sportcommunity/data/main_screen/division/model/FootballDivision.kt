package com.glushko.sportcommunity.data.main_screen.division.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class FootballDivision(
    @SerializedName("division_id")val divisionId: Int,
    @SerializedName("division_name") val divisionName: String
) : Parcelable

fun FootballDivision.toModel() = DivisionDisplayData(
    name = divisionName,
    id = divisionId
)
