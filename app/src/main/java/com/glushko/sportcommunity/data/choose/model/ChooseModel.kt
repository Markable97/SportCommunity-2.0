package com.glushko.sportcommunity.data.choose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseModel(
    val valueDisplay: String,
    val valueType: Int,
    val position: Int? = null,
    var isChoose: Boolean = false
) : Parcelable