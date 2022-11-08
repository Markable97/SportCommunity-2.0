package com.glushko.sportcommunity.data.admin.edit_match.model

import com.glushko.sportcommunity.data.match_detail.model.PlayerAction

data class PlayerWithActionUI(
    var time: String? = null,
    var player: PLayerUI? = null,
    var playerAssist: PLayerUI? = null,
    var action: ActionUI? = null,
    var isSaving: Boolean = false
)
