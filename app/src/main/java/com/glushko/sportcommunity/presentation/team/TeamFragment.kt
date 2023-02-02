package com.glushko.sportcommunity.presentation.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.glushko.sportcommunity.presentation.base.statistics.StatisticsTournamentAdapter
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.addOnPageSelectedListener
import com.glushko.sportcommunity.util.extensions.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamFragment: BaseXmlFragment<FragmentTeamBinding>(R.layout.fragment_team) {

    private val args: TeamFragmentArgs by navArgs()

    private val viewModel: TeamViewModel by viewModels()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTeamBinding {
        return FragmentTeamBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.teamId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupObservers()
        setupListeners()
    }

    private fun initView() = binding.run {
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.teamName)
        imageTeam.load(args.teamImage?:"${Constants.BASE_URL_IMAGE}${args.teamName}.png")
        textTeamName.text = args.teamName
    }

    private fun setupListeners() = binding.run {
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
    }

    private fun setupObservers() = viewModel.run {
        liveDataTable.observe(viewLifecycleOwner){
            when(it){
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    renderTournamentTable(it.data!!)
                }
            }
        }
        liveDataSquadInfo.observe(viewLifecycleOwner){
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
    }

    private fun renderTournamentTable(data: List<TournamentTableDisplayData>) = binding.itemTournamentTable.run {
        renderRow(data[0],  itemRowFirst)
        renderRow(data[1],  itemRowSecond)
        renderRow(data[2],  itemRowThird)
        renderRow(data[3],  itemRowFourth)
    }

    private fun renderRow(row: TournamentTableDisplayData, bindingRow: ItemTournamentTableRowBinding){
        bindingRow.apply {
            if (row.teamId == args.teamId) {
                root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.drawer_item_selected))
                textTeamName.setTextColor(ContextCompat.getColor(requireContext(), R.color.bg_tournament_table_team_selected))
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

    private fun renderStatistics(data: List<PlayerStatisticAdapter>) = binding.itemStatistics.run {
        viewPagerStatistics.adapter = StatisticsTournamentAdapter(Constants.OPEN_FROM_TEAM).apply { submitList(data) }
        viewPagerStatistics.addOnPageSelectedListener {  }
        TabLayoutMediator(tabLayoutStatistics, viewPagerStatistics) { _, _ -> }.attach()
    }


}