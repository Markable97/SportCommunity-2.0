package com.glushko.sportcommunity.data.admin.edit_match.model

import com.glushko.sportcommunity.data.match_detail.model.PlayerAction

data class PlayerWithActionUI(
    val time: String? = null,
    val player: PLayerUI? = null,
    val playerAssist: PLayerUI? = null,
    val action: ActionUI? = null,
    val isSaving: Boolean = false
)
