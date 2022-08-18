package com.glushko.sportcommunity.presentation.tournament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.databinding.FragmentTournamentBinding
import com.glushko.sportcommunity.databinding.ItemTournamentTableRowBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.main_screen.vm.MainViewModel
import com.glushko.sportcommunity.presentation.tournament.adapters.StatisticsTournamentAdapter
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.extensions.addOnPageSelectedListener
import com.glushko.sportcommunity.util.extensions.toast
import com.google.android.material.tabs.TabLayoutMediator

class TournamentFragment: BaseXmlFragment<FragmentTournamentBinding>(R.layout.fragment_tournament) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTournamentBinding = FragmentTournamentBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListener()
    }

    private fun setupObservers() = viewModel.run {
        liveDataTable.observe(viewLifecycleOwner){
            when(it){
                is Resource.Empty -> {}
                is Resource.Error -> {
                    toast(requireContext(), it.error!!.message ?: "")
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    renderTournamentTable(it.data!!)
                }
            }
        }
        liveDataStatistics.observe(viewLifecycleOwner){
            when(it){
                is Resource.Empty -> {}
                is Resource.Error -> {
                    toast(requireContext(), it.error!!.message ?: "")
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    renderStatistics(it.data!!)
                }
            }
        }
    }

    private fun setupListener() {
        binding.apply {

        }
        binding.itemTournamentTable.textTitle.setOnClickListener {

        }
        binding.itemStatistics.textTitle.setOnClickListener {

        }
    }

    private fun renderTournamentTable(data: List<TournamentTableDisplayData>) = binding.itemTournamentTable.run {
        renderRow(data[0], 1, itemRowFirst)
        renderRow(data[1], 2, itemRowSecond)
        renderRow(data[2], 3, itemRowThird)
        renderRow(data[3], 4, itemRowFourth)
    }

    private fun renderRow(row: TournamentTableDisplayData, position: Int, bindingRow: ItemTournamentTableRowBinding){
        bindingRow.apply {
            textPosition.text = position.toString()
            imageTeam.load("${Constants.BASE_URL_IMAGE}${row.teamName}.png")
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
        viewPagerStatistics.adapter = StatisticsTournamentAdapter().apply { submitList(data) }
        viewPagerStatistics.addOnPageSelectedListener {  }
        TabLayoutMediator(tabLayoutStatistics, viewPagerStatistics) { _, _ -> }.attach()
    }

}