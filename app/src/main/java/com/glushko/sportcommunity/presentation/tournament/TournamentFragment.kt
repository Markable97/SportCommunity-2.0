package com.glushko.sportcommunity.presentation.tournament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.databinding.FragmentTournamentBinding
import com.glushko.sportcommunity.databinding.ItemTournamentTableRowBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.main_screen.vm.MainViewModel
import com.glushko.sportcommunity.presentation.base.statistics.StatisticsTournamentAdapter
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.extensions.addOnPageSelectedListener
import com.glushko.sportcommunity.util.extensions.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentFragment: BaseXmlFragment<FragmentTournamentBinding>(R.layout.fragment_tournament) {

    private val viewModelMain: MainViewModel by activityViewModels()
    private val viewModel: TournamentViewModel by hiltNavGraphViewModels(R.id.nav_graph_tournament)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTournamentBinding = FragmentTournamentBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListener()
    }

    private fun setupObservers() {
        viewModelMain.run {
            liveDataMainScreen.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Empty -> {}
                    is Resource.Error -> {
                        toast(requireContext(), it.error!!.message ?: "")
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        viewModel.init()
                    }
                }
            }
        }
        viewModel.run {
            liveDataTable.observe(viewLifecycleOwner){
                renderTournamentTable(it)
            }
            liveDataStatistics.observe(viewLifecycleOwner){
                renderStatistics(it)
            }
        }
    }

    private fun setupListener() = binding.run {
        itemTournamentTable.textTitle.setOnClickListener {
            findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTournamentTableFragment())
        }
        itemStatistics.textTitle.setOnClickListener {
            viewModelMain.liveDataSelectedDivision.value?.let { divisionId ->
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToStatisticsFragment(
                    null,
                    Constants.OPEN_FROM_TOURNAMENT,
                    divisionId
                ))
            }
        }
        buttonMedia.setOnClickListener {
            findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTournamentMediaFragment())
        }
    }

    private fun renderTournamentTable(data: List<TournamentTableDisplayData>) = binding.itemTournamentTable.run {
        data.forEachIndexed { index, tournamentTableDisplayData ->
            when(index){
                0 -> renderRow(tournamentTableDisplayData, 1, itemRowFirst)
                1 -> renderRow(tournamentTableDisplayData, 2, itemRowSecond)
                2 -> renderRow(tournamentTableDisplayData, 3, itemRowThird)
                3 -> renderRow(tournamentTableDisplayData, 4, itemRowFourth)
            }
        }
    }

    private fun renderRow(row: TournamentTableDisplayData, position: Int, bindingRow: ItemTournamentTableRowBinding){
        bindingRow.apply {
            root.setOnClickListener{
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTeamFragment(
                    row.teamName, row.teamId, row.teamImage
                ))
            }
            textPosition.text = position.toString()
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

    private fun renderStatistics(data: List<PlayerStatisticAdapter>) = binding.itemStatistics.run {
        viewPagerStatistics.adapter = StatisticsTournamentAdapter(Constants.OPEN_FROM_TOURNAMENT).apply { submitList(data) }
        viewPagerStatistics.addOnPageSelectedListener {  }
        TabLayoutMediator(tabLayoutStatistics, viewPagerStatistics) { _, _ -> }.attach()
    }

}