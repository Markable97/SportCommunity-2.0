package com.glushko.sportcommunity.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentPlayerInfoBinding
import com.glushko.sportcommunity.databinding.ItemStatisticPlayerBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.base.menu.MenuHostFragment
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI
import com.glushko.sportcommunity.presentation.player.matches.model.PlayerActionsInMatchUI
import com.glushko.sportcommunity.presentation.player.model.PlayerInfoUI
import com.glushko.sportcommunity.presentation.player.model.PlayerStatisticsUI
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.getFullUrl
import com.glushko.sportcommunity.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

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


    private fun renderCompose(
        currentTeam: CareerWidgetUI,
        matchesPlayer: List<PlayerActionsInMatchUI>,
        playerInfo: PlayerInfoUI,
        statistics: List<PlayerStatisticsUI>
    ) = binding.run {
        composeCareerWidget.setContent {
            PlayerScreen(
                navController = findNavController(),
                currentTeam = currentTeam,
                matches = matchesPlayer,
                playerInfo = playerInfo,
                statistics = statistics
            )
        }
    }

    private fun setupObservers() {
        viewModel.liveDataInfoPlayer.observe(viewLifecycleOwner) {
            showProgress(it is Result.Loading)
            when(it) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    renderCompose(it.data.currentTeam, it.data.playerActions, it.data.info, it.data.statistics)
                }
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

}