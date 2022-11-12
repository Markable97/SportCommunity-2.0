package com.glushko.sportcommunity.data.admin.edit_match.model

import com.glushko.sportcommunity.data.admin.edit_match.network.RequestPlayerWithAction

data class PlayerWithActionUI(
    var time: String? = null,
    var player: PLayerUI? = null,
    var playerAssist: PLayerUI? = null,
    var action: ActionUI? = null,
    var isSaving: Boolean = false
) {
    fun toRequestModel(matchId: Long, isAdd: Boolean): RequestPlayerWithAction?{
        if (time == null || player == null || action == null) return null
        return RequestPlayerWithAction(
            matchId = matchId,
            playerId = player?.playerId ?: 0,
            playerAssistId = playerAssist?.playerId ?: 0,
            actionId = action?.actionId ?: 0,
            time = time ?: "",
            isAdd = isAdd
        )
    }
}
