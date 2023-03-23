package com.glushko.sportcommunity.presentation.player.matches.model

import android.os.Parcelable
import com.glushko.sportcommunity.data.player.network.PlayerPointsActions
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerActionsInMatchUI(
    val match: MatchFootballDisplayData,
    val actions: PlayerPointsActions
) : Parcelable
