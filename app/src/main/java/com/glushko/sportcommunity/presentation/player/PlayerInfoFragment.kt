package com.glushko.sportcommunity.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuHost
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentPlayerInfoBinding
import com.glushko.sportcommunity.databinding.ItemStatisticPlayerBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.base.menu.MenuHostFragment
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.player.career.CareerWidget
import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI
import com.glushko.sportcommunity.presentation.player.matches.model.PlayerActionsInMatchUI
import com.glushko.sportcommunity.presentation.player.model.PlayerInfoUI
import com.glushko.sportcommunity.presentation.player.model.PlayerStatisticsUI
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.getFullUrl
import com.glushko.sportcommunity.util.extensions.snackbar
import com.glushko.sportcommunity.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

//TODO XML to Compose
@AndroidEntryPoint
class PlayerInfoFragment : BaseXmlFragment<FragmentPlayerInfoBinding>(R.layout.fragment_player_info), MenuHostFragment {

    private val args: PlayerInfoFragmentArgs by navArgs()

    private val viewModel by viewModels<PlayerInfoViewModel>()

    override val menuRes: Int
        get() = R.menu.menu_web_link
    override val menuActions: Map<Int, (MenuItem) -> Boolean>
        get() = mapOf(
            R.id.menuWeb to {
               (requireActivity() as? MainActivity)?.openWeb( args.playerUrl.getFullUrl())
                true
            }
        )

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayerInfoBinding {
        return FragmentPlayerInfoBinding.inflate(inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? MenuHost)?.let { host ->
            setupMenu(host, viewLifecycleOwner)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.playerName)
        setupObservers()
    }


    private fun renderCompose(currentTeam: CareerWidgetUI, matchesPlayer: List<PlayerActionsInMatchUI>) = binding.run {
        composeCareerWidget.setContent {
                    Column(
                        modifier = Modifier.padding(bottom = 5.dp, start = 5.dp, end = 5.dp).background(Color.Transparent)
                    ) {
                        CareerWidget(
                            widgetInfo = currentTeam,
                            clickAction = {
                                snackbar(binding.root, "Пока в разработке")
                            },
                            clickTeamAction = { careerInfo ->
                                findNavController().navigate(
                                    PlayerInfoFragmentDirections.actionPlayerInfoFragmentToTeamFragment(
                                        teamId = careerInfo.teamId,
                                        teamName = careerInfo.teamName,
                                        teamImage = careerInfo.teamImage
                                    )
                                )
                            }

                        )
                        matchesPlayer.firstOrNull()?.let {matchPlayer ->
                            Spacer(modifier = Modifier.padding(5.dp))
                            LastMatchWidget(
                                match = matchPlayer.match,
                                navController = findNavController(),
                                clickMatchDirection = PlayerInfoFragmentDirections.actionPlayerInfoFragmentToDetailMatchFragment(matchPlayer.match),
                                playerActions = matchPlayer.actions,
                                clickActionTitle = {
                                    findNavController().navigate(PlayerInfoFragmentDirections.actionPlayerInfoFragmentToMatchesPlayerFragment(matchesPlayer.toTypedArray()))
                                }
                            )
                        }
                    }
        }
    }

    private fun setupObservers() {
        viewModel.liveDataInfoPlayer.observe(viewLifecycleOwner) {
            showProgress(it is Result.Loading)
            when(it) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    renderHeaderInfo(it.data.info)
                    renderStatisticsZone(it.data.statistics)
                    renderCompose(it.data.currentTeam, it.data.playerActions)
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