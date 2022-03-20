package com.glushko.sportcommunity.presentation.tournament_table.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.BaseFragment
import com.glushko.sportcommunity.presentation.tournament_table.vm.TournamentTableViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TournamentTableFragment : BaseFragment() {

    private val viewModel: TournamentTableViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tournament_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.liveDataSelectedDivision.observe(viewLifecycleOwner){
            Timber.d("Пришел новый дивизион = $it")
            viewModel.getTournamentTable(it)
        }
        viewModel.liveDataTable.observe(viewLifecycleOwner){
            Timber.d("Турнирная таблица = $it")
        }
    }
}