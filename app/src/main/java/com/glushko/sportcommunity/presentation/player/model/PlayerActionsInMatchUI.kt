package com.glushko.sportcommunity.presentation.player.model

import com.glushko.sportcommunity.data.player.network.PlayerPointsActions
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData

data class PlayerActionsInMatchUI(
    val match: MatchFootballDisplayData,
    val actions: PlayerPointsActions
)
