package com.glushko.sportcommunity.presentation.matches.calendar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.BaseFragment
import com.glushko.sportcommunity.presentation.matches.CardMatch
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

@Composable
fun DateAndStadium(matchDate: String, stadium: String, modifier: Modifier) {
    Row(modifier = modifier) {
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Column(modifier = Modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
            Text(text = matchDate, modifier = Modifier
                .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())
            Text(text = stadium, modifier = Modifier
                .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}