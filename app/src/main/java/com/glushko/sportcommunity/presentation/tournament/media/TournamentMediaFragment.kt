package com.glushko.sportcommunity.presentation.tournament.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.media.CreateMediaAlbumScreen
import com.glushko.sportcommunity.presentation.tournament.TournamentFragmentDirections
import com.glushko.sportcommunity.presentation.tournament.TournamentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentMediaFragment : BaseFragment() {

    private val viewModel: TournamentViewModel by hiltNavGraphViewModels(R.id.nav_graph_tournament)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenMedia()
                }
            }
        }
    }

    @Composable
    private fun ScreenMedia(){
        val mediaList: List<MediaUI> by viewModel.liveDataMedia.observeAsState(emptyList())
        CreateMediaAlbumScreen(mediaList){ matchId, title ->
            mainViewModel.getMatchMedia(matchId)
            (requireActivity() as? MainActivity)?.apply {
                setToolbarTitle(title)
            }
            findNavController().navigate(TournamentMediaFragmentDirections.actionTournamentMediaFragmentToGalleryFragment())
        }
    }

}