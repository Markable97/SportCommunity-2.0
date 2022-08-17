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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.matches.CardMatch
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
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
    private fun ScreenCalendar(){
        val response by mainViewModel.liveDataCalendar.observeAsState(Resource.Empty())
        CreateScreen(response = response)

    }
    @Composable
    private fun CreateScreen(response: Resource<List<MatchFootballDisplayData>>){
        Column {
            when(response){
                is Resource.Empty -> {}
                is Resource.Error -> {
                    DoSomething(message = response.error?.message?:"", textButton = "Повторить"){
                        mainViewModel.getCalendarRetry()
                    }
                }
                is Resource.Loading -> {
                    Loader()
                }
                is Resource.Success -> {
                    CalendarList(matches = response.data!!)
                }
            }
        }
    }

    @Composable
    private fun CalendarList(matches: List<MatchFootballDisplayData>){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            items(matches){match ->
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