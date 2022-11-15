package com.glushko.sportcommunity.data.admin.edit_match.model

import com.glushko.sportcommunity.data.admin.edit_match.network.RequestPlayerInMatch
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.util.Constants

data class PLayerUI(
    val playerId: Int,
    val playerName: String,
    val teamId: Int,
    val teamName: String,
    var inMatch: Boolean = false
) {
    fun toRequestModel(matchId: Long) = RequestPlayerInMatch(
        teamId, teamName, playerId, playerName, matchId
    )

    fun toChooseModel(index: Int) = ChooseModel(
        valueDisplay = playerName,
        valueType = Constants.TYPE_VALUE_PLAYER,
        position = index
    )

    fun toMultiChooseModel(index: Int) = ChooseModel(
        valueDisplay = playerName,
        valueType = Constants.TYPE_VALUE_PLAYER,
        position = index,
        isChoose = inMatch
    )

}