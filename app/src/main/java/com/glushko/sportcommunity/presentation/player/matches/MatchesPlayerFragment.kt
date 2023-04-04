package com.glushko.sportcommunity.presentation.player.matches

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.bgMainGradient
import com.glushko.sportcommunity.presentation.matches.CardMatch

class MatchesPlayerFragment : BaseFragment() {

    private val matchesPlayerArgs by navArgs<MatchesPlayerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenMatchesPlayer()
                }
            }
        }
    }

    @Composable
    private fun ScreenMatchesPlayer() {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(brush = bgMainGradient())){
            items(matchesPlayerArgs.matches){ match ->
                CardMatch(match.match, findNavController(), MatchesPlayerFragmentDirections.actionMatchesPlayerToDetailMatchFragment(match.match), match.actions)
            }
        }
    }

}