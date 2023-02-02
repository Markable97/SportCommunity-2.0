package com.glushko.sportcommunity.data.admin.edit_match.network

import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.google.gson.annotations.SerializedName

class ResponseActions(
    success: Int,
    message: String,
    val actions: List<Action> = emptyList()
) : BaseResponse(success, message) {
}

fun ResponseActions.toModel() = actions.map { it.toModel() }

data class Action(
    @SerializedName("action_id")
    val actionId: Int,
    @SerializedName("action_name")
    val actionName: String
)

fun Action.toModel() = ActionUI(actionId, actionName)