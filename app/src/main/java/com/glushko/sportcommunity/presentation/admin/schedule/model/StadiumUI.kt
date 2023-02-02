package com.glushko.sportcommunity.presentation.admin.schedule.model

import android.os.Parcelable
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.model.ChooseModel
import com.glushko.sportcommunity.util.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class StadiumUI(
    val id: Int,
    val name: String
): Parcelable

fun StadiumUI.toChooseModel(index: Int) = ChooseModel(
    valueType = Constants.TYPE_VALUE_STADIUM,
    valueDisplay = name,
    position = index
)
