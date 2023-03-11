package com.glushko.sportcommunity.presentation.team.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.EmptyText
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.media.CreateMediaAlbumScreen
import dagger.hilt.android.AndroidEntryPoint
import com.glushko.sportcommunity.util.Result
import timber.log.Timber

@AndroidEntryPoint
class TeamMediaFragment : BaseFragment() {

    private val viewModel: TeamMediaViewModel by viewModels()

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
        val response by viewModel.liveDataMedia.observeAsState(Result.Loading)
        Timber.d("Media team = $response")
        CreateScreen(response)
    }

    @Composable
    private fun CreateScreen(response: Result<List<MediaUI>>) {
        when(response) {
            is Result.Error -> {
                DoSomething(message = response.exception.message ?: "") {
                    viewModel.getMediaTeam()
                }
            }
            Result.Loading -> {
                Loader()
            }
            is Result.Success -> {
                ItemsMedia(mediaList = response.data)
            }
        }
    }

    @Composable
    private fun ItemsMedia(mediaList: List<MediaUI>) {
        if (mediaList.isNotEmpty()) {
            CreateMediaAlbumScreen(mediaList){ matchId, title ->
                (requireActivity() as? MainActivity)?.apply {
                    setToolbarTitle(title)
                }
            findNavController().navigate(TeamMediaFragmentDirections.actionTeamMediaFragmentToGalleryFragment(matchId))
            }
        } else {
            EmptyText(textMessage = getString(R.string.media_empty))
        }
    }

}