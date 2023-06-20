package com.glushko.sportcommunity.presentation.team.squad

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.presentation.core.SearchDisplay
import com.glushko.sportcommunity.presentation.core.SearchState
import com.glushko.sportcommunity.presentation.core.SearchTextField
import com.glushko.sportcommunity.presentation.core.rememberSearchState

@Composable
fun SquadScreen(
    modifier: Modifier = Modifier,
    viewModel: SquadViewModel,
    state: SearchState<SquadPlayerUI> = rememberSearchState(
        searchResults = viewModel.initialSquad
    ),
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        SearchTextField(
            query = state.query,
            onQueryChange = { state.query = it },
            onClearQuery = { state.query = TextFieldValue("") },
            searching = state.searching,
            focused = state.focused,
            hint = stringResource(id = R.string.squad__search_hint),
            modifier = modifier
        )

        LaunchedEffect(state.query.text) {
            state.searching = true
            state.searchResults = viewModel.searchPlayer(state.query.text)
            state.searching = false
        }

        when (state.searchDisplay) {
            SearchDisplay.NoResults -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ничего не найдено",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            /*SearchDisplay.Suggestions -> {

            }*/
            SearchDisplay.Results -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(state.searchResults) { player ->
                        ItemSquad(data = player) {
                            navController.navigate(
                                SquadFragmentDirections.actionSquadFragmentToPlayerInfoFragment(
                                    it.playerId,
                                    it.playerName,
                                    it.playerUrl
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}