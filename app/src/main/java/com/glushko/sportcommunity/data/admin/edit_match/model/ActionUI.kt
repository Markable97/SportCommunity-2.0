package com.glushko.sportcommunity.data.admin.edit_match.model

import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.util.Constants

data class ActionUI(
    val actionId: Int,
    val actionName: String
)

fun ActionUI.toChooseModel(index: Int) = ChooseModel(
    valueDisplay = actionName,
    valueType = Constants.TYPE_VALUE_ACTION,
    position = index
)
