package com.glushko.sportcommunity.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerInfoFragment : Fragment() {

    private val args: PlayerInfoFragmentArgs by navArgs()

    private val viewModel by viewModels<PlayerInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenPlayerInfo()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.playerName)
    }

    @Composable
    private fun ScreenPlayerInfo() {
        val response: Result<Unit> by viewModel.liveDataInfoPlayer.observeAsState(Result.Loading)
        when(response) {
            is Result.Error -> {
                DoSomething(message = (response as Result.Error).exception.message?:"", textButton = "Повторить") {
                    viewModel.getPlayerInfo(-1)
                }
            }
            Result.Loading -> {
                Loader()
            }
            is Result.Success -> {
                Text(text = "Информация о игроке id = ${args.playerId}")
            }
        }
    }

}