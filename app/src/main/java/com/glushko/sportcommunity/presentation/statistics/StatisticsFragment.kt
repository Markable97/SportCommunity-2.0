package com.glushko.sportcommunity.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.databinding.FragmentStatisticsBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.base.statistics.StatisticsAllAdapter
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment: BaseXmlFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()

    private val args: StatisticsFragmentArgs by navArgs()

    private val adapterStatistics by lazy {
        StatisticsAllAdapter(args.openFrom)
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatisticsBinding = FragmentStatisticsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
        setupListeners()
    }

    private fun initViews() {
        binding.textTitle.text = getText(
            if (args.openFrom == Constants.OPEN_FROM_TOURNAMENT){
                R.string.tournament__statistics
            }else {
                R.string.team_leaders
            }
        )
        binding.recyclerStatistics.adapter = adapterStatistics
    }

    private fun setupObservers() = viewModel.run{
        liveDataStatisticsPlayers.observe(viewLifecycleOwner){
            when(it){
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapterStatistics.submitList(it.data)
                }
            }
        }
    }

    private fun setupListeners() = binding.run {
        tabLayoutStatistics.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {viewModel.getStatistics(TypeStatistics.GOALS)}
                    1 -> {viewModel.getStatistics(TypeStatistics.ASSISTS)}
                    2 -> {viewModel.getStatistics(TypeStatistics.YELLOW_CARDS)}
                    3 -> {viewModel.getStatistics(TypeStatistics.RED_CARDS)}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        }
        )
    }
}