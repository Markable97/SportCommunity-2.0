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
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentPlayerInfoBinding
import com.glushko.sportcommunity.databinding.ItemStatisticPlayerBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.player.model.PlayerInfoUI
import com.glushko.sportcommunity.presentation.player.model.PlayerStatisticsUI
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerInfoFragment : BaseXmlFragment<FragmentPlayerInfoBinding>(R.layout.fragment_player_info) {

    private val args: PlayerInfoFragmentArgs by navArgs()

    private val viewModel by viewModels<PlayerInfoViewModel>()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayerInfoBinding {
        return FragmentPlayerInfoBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.playerName)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.liveDataInfoPlayer.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    renderHeaderInfo(it.data.info)
                    renderStatisticsZone(it.data.statistics)
                }
            }
        }
    }

    private fun renderHeaderInfo(playerInfo: PlayerInfoUI) = binding.run {
        textPlayerName.text = playerInfo.name
        imagePlayer.load(playerInfo.imageUrl)
        textBirthday.text = playerInfo.birthday
        textAmplua.text = playerInfo.amplua
    }

    private fun renderStatisticsZone(statistics: List<PlayerStatisticsUI>) = binding.run {
        statistics.forEach { statistic ->
            when(statistic) {
                PlayerStatisticsUI.GAMES -> addStatisticsInfo(viewGames, statistic)
                PlayerStatisticsUI.GOALS -> addStatisticsInfo(viewGoals, statistic)
                PlayerStatisticsUI.ASSISTS -> addStatisticsInfo(viewAssists, statistic)
                PlayerStatisticsUI.RED_CARDS -> addStatisticsInfo(viewRedCards, statistic)
                PlayerStatisticsUI.YELLOW_CARDS -> addStatisticsInfo(viewYellowCards, statistic)
                PlayerStatisticsUI.PENALTY -> addStatisticsInfo(viewPenalty, statistic)
                PlayerStatisticsUI.PENALTY_OUT -> addStatisticsInfo(viewPenaltyOut, statistic)
                PlayerStatisticsUI.OWN_GOALS -> addStatisticsInfo(viewOwnGoals, statistic)
            }
        }
    }

    private fun addStatisticsInfo(bindingItem: ItemStatisticPlayerBinding, data: PlayerStatisticsUI) {
        bindingItem.apply {
            root.visible()
            textAction.text = getString(data.actionName)
            textCount.text = data.points.toString()
            imageAction.setImageResource(data.picture)
        }
    }

    /*override fun onCreateView(
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
    }*/

    /*@Composable
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
    }*/

}