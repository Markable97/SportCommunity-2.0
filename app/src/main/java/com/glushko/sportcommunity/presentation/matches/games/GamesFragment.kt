package com.glushko.sportcommunity.presentation.matches.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.matches.CardMatch
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private val viewModel: GamesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenResult()
                }
            }
        }
    }

    @Composable
    private fun ScreenResult() {
        val response by viewModel.liveDataGames.observeAsState(Result.Loading)
        CreateScreen(response = response)
    }
    @Composable
    private fun CreateScreen(response: Result<List<MatchFootballDisplayData>>){
        when(response){
            is Result.Error -> {
                DoSomething(message = response.exception.message ?: "", textButton = "Повторить") {
                    viewModel.getMatches()
                }
            }
            is Result.Loading -> {
                Loader()
            }
            is Result.Success -> {
                ResultsList(response.data)
            }
        }
    }

    @Composable
    private fun ResultsList(matches: List<MatchFootballDisplayData>) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            items(matches){match ->
                CardMatch(
                    match,
                    findNavController(),
                    if (match.played==Constants.TYPE_MATCH_PLAYED) {
                        GamesFragmentDirections.actionGamesFragmentToDetailMatchFragment(match)
                    } else {
                        null
                    }
                )
            }
        }
    }

}