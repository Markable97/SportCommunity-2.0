package com.glushko.sportcommunity.presentation.calendar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.BaseFragment
import com.glushko.sportcommunity.presentation.results.ui.CardMatch
import timber.log.Timber

class CalendarFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenCalendar()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.liveDataSelectedDivision.observe(viewLifecycleOwner){
            Timber.d("Пришел новый дивизион = $it")
        }
    }

    @Composable
    fun ScreenCalendar(){
        val response = listOf(MatchFootballDisplayData(
            played = 0,
            stadium = "Спартак 1",
            matchDate = "06.02.2021",
            tour = 25,
            teamGuestGoal = 0,
            teamGuestName = "Команда гстей",
            teamHomeGoal = 0,
            teamHomeName = "Команда домашняя",
            divisionName = "Какой то там дивизион",
            leagueName = "Бомжицкая",
            matchId = 235
        ))
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            items(response){match ->
                CardMatch(match, findNavController())
            }
        }
    }


}