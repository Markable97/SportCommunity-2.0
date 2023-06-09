package com.glushko.sportcommunity.presentation.team

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuHost
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.databinding.FragmentTeamBinding
import com.glushko.sportcommunity.databinding.ItemTournamentTableRowBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.base.menu.MenuHostFragment
import com.glushko.sportcommunity.presentation.base.statistics.StatisticsTournamentAdapter
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.team.model.TeamDisplayData
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import com.glushko.sportcommunity.util.extensions.addOnPageSelectedListener
import com.glushko.sportcommunity.util.extensions.getFullUrl
import com.glushko.sportcommunity.util.extensions.setSelection
import com.glushko.sportcommunity.util.extensions.showDebugUnderDevelopmentMessage
import com.glushko.sportcommunity.util.extensions.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamFragment: BaseXmlFragment<FragmentTeamBinding>(R.layout.fragment_team), MenuHostFragment {

    private val args: TeamFragmentArgs by navArgs()

    private val viewModel: TeamViewModel by viewModels()

    override val menuRes: Int
        get() = R.menu.menu_web_link
    override val menuActions: Map<Int, (MenuItem) -> Boolean>
        get() = mapOf(
            R.id.menuWeb to {
                viewModel.liveDataSquadInfo.value?.data?.teamUrl?.let { url ->
                    (requireActivity() as? MainActivity)?.openWeb(url.getFullUrl())
                }
                true
            }
        )

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTeamBinding {
        return FragmentTeamBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.teamId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? MenuHost)?.let {
            setupMenu(it, viewLifecycleOwner)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.teamName)
        renderCompose()
    }

    private fun renderCompose() = binding.composeView.setContent {
        val teamDisplayResponse by viewModel.liveDataTeamDisplayData.observeAsState(Result.Loading)
        CreateScreen(teamDisplayResponse)
    }

    @Composable
    private fun CreateScreen(teamDisplayResponse: Result<TeamDisplayData>) {
        when(teamDisplayResponse) {
            is Result.Error -> {
                DoSomething(message = teamDisplayResponse.exception.message ?: "") {
                    viewModel.init(args.teamId)
                }
            }
            Result.Loading -> {
                Loader()
            }
            is Result.Success -> {
                TeamScreen(
                    teamArg = args,
                    navController = findNavController(),
                    tournamentTable = teamDisplayResponse.data.tournamentTable,
                    statistics = teamDisplayResponse.data.statistics
                )
            }
        }
    }

    /*private fun initView() = binding.run {
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.teamName)
        imageTeam.load(args.teamImage?:"${Constants.BASE_URL_IMAGE}${args.teamName}.png")
        textTeamName.text = args.teamName
    }*/

    /*private fun setupListeners() = binding.run {
        itemStatistics.textTitle.setOnClickListener {
            findNavController().navigate(
                TeamFragmentDirections.actionTeamFragmentToStatisticsFragment(
                    args.teamName,
                    Constants.OPEN_FROM_TEAM,
                    args.teamId
                )
            )
        }
        buttonSquad.setOnClickListener{
            findNavController().navigate(
                TeamFragmentDirections.actionTeamFragmentToSquadFragment(
                    args.teamId
                )
            )
        }
        buttonGames.setOnClickListener {
            findNavController().navigate(TeamFragmentDirections.actionTeamFragmentToGamesFragment(teamId = args.teamId))
        }
        buttonMedia.setOnClickListener {
            findNavController().navigate(TeamFragmentDirections.actionTeamFragmentToTeamMediaFragment(args.teamId))
        }
        buttonHistory.setOnClickListener {
            binding.root.showDebugUnderDevelopmentMessage()
        }
    }*/

    /*private fun setupObservers() = viewModel.run {
        liveDataTable.observe(viewLifecycleOwner){
            showProgress(loadingVisible())
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    renderTournamentTable(it.data)
                }
            }
        }
        liveDataSquadInfo.observe(viewLifecycleOwner){
            showProgress(loadingVisible())
            when(it){
                is Result.Error -> {
                    toast(requireContext(), it.exception.message ?: getString(R.string.error_network_default))
                }
                Result.Loading -> {}
                is Result.Success -> {
                    viewModel.getStatistics()
                }
            }
        }
        liveDataStatistics.observe(viewLifecycleOwner){
            when(it){
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    renderStatistics(it.data!!)
                }
            }
        }
    }*/

    /*private fun renderTournamentTable(data: List<TournamentTableDisplayData>) = binding.itemTournamentTable.run {
        data.forEachIndexed{ index, data ->
            when(index) {
                0 -> renderRow(data,  itemRowFirst)
                1 -> renderRow(data,  itemRowSecond)
                2 -> renderRow(data,  itemRowThird)
                3 -> renderRow(data,  itemRowFourth)
                else -> return@forEachIndexed
            }
        }
    }*/

    private fun renderRow(row: TournamentTableDisplayData, bindingRow: ItemTournamentTableRowBinding){
        with(bindingRow) {
            if (row.teamId == args.teamId) {
                textPosition.setSelection()
                textTeamName.setSelection()
                textGames.setSelection()
                textWins.setSelection()
                textDraws.setSelection()
                textLoses.setSelection()
                textDifferences.setSelection()
                textPoints.setSelection()

            }
            textPosition.text = row.position.toString()
            val image = row.teamImage ?: "${Constants.BASE_URL_IMAGE}${row.teamName}.png"
            imageTeam.load(image)
            textTeamName.text = row.teamName
            textGames.text = row.games.toString()
            textWins.text = row.wins.toString()
            textDraws.text = row.draws.toString()
            textLoses.text = row.losses.toString()
            textDifferences.text = row.scCon.toString()
            textPoints.text = row.points.toString()
        }
    }

    /*private fun renderStatistics(data: List<PlayerStatisticAdapter>) = binding.itemStatistics.run {
        viewPagerStatistics.adapter = StatisticsTournamentAdapter(
            fromOpen = Constants.OPEN_FROM_TEAM,
            onClickPlayer = { id, name, url ->
                findNavController().navigate(
                    TeamFragmentDirections.actionTeamFragmentToPlayerInfoFragment(id, name, url)
                )
            }
        ).apply { submitList(data) }
        viewPagerStatistics.addOnPageSelectedListener {  }
        TabLayoutMediator(tabLayoutStatistics, viewPagerStatistics) { _, _ -> }.attach()
    }*/


}