package com.glushko.sportcommunity.presentation.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.player.network.PlayerPointsActions
import com.glushko.sportcommunity.presentation.core.Widget
import com.glushko.sportcommunity.presentation.matches.CardMatchWithPlayerActions
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData


@Composable
fun LastMatchWidget(
    match: MatchFootballDisplayData,
    navController: NavController,
    clickActionTitle: (()->Unit)? = null,
    clickMatchDirection: NavDirections?,
    playerActions: PlayerPointsActions
){
    Widget(
        title = stringResource(id = R.string.player__last_activity),
        backgroundColor = colorResource(id = R.color.primary_color),
        navigationAction = clickActionTitle
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardMatchWithPlayerActions(
                match = match,
                navController = navController,
                action = clickMatchDirection,
                playerActions = playerActions
            )
        }
    }
}