package com.glushko.sportcommunity.presentation.player.model

import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI
import com.glushko.sportcommunity.presentation.player.matches.model.PlayerActionsInMatchUI

data class ProfilePlayerUI(
    val info: PlayerInfoUI,
    val statistics: List<PlayerStatisticsUI>,
    val currentTeam: CareerWidgetUI,
    val playerActions: List<PlayerActionsInMatchUI>
)

data class PlayerInfoUI(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val birthday: String,
    val amplua: String
)


