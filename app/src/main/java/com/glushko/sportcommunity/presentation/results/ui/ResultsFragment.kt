package com.glushko.sportcommunity.presentation.results.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.results.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.BaseFragment
import com.glushko.sportcommunity.presentation.results.vm.ResultsViewModel
import com.glushko.sportcommunity.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ResultsFragment : BaseFragment() {

    private val viewModel: ResultsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenResult()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.liveDataSelectedDivision.observe(viewLifecycleOwner) {
            Timber.d("Пришел новый дивизион = $it")
            viewModel.getResults(it)
        }
    }

    @Composable
    fun ScreenResult() {
        val response by viewModel.liveDataResults.observeAsState(emptyList())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            items(response){match ->
                CardResult(match)
            }
        }
    }

    @Composable
    fun CardResult(match: MatchFootballDisplayData){
        Card(
            modifier = Modifier
                .height(100.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.LightGray

        ){
            Column(
            )
                {
                ResultHeader(match.divisionName)
                Row() {
                    val modifierTour= Modifier
                        .weight(0.1f)
                    TextTour(tour = match.tour,modifier = modifierTour)
                    val modifierTeams= Modifier
                        .weight(0.8f)
                    Teams(teamHome = match.teamHomeName, teamGuest = match.teamGuestName, modifierTeams)
                    val modifierScore= Modifier
                        .weight(0.1f)
                    Score(match.teamHomeGoal, match.teamGuestGoal,modifierScore)
                }
            }
        }
    }

    @Composable
    fun ResultHeader(divisionName: String) {
        Column {
            Text(text = divisionName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Divider(color = Color.Gray)
        }
    }

    @Composable
    fun TextTour(tour: Int, modifier: Modifier){
        Box(contentAlignment = Alignment.Center, modifier =  modifier.fillMaxHeight()) {
            Text(text = "$tour тур", textAlign = TextAlign.Center)
        }

    }

    @Composable
    fun Teams(teamHome: String, teamGuest: String, modifier: Modifier){
        Column(modifier = modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
            Team(teamName = teamHome)
            Spacer(modifier = Modifier.height(1.dp))
            Team(teamName = teamGuest)
        }
    }

    @Composable
    fun Team(teamName: String){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = "${Constants.BASE_URL_IMAGE}$teamName.png",
//                placeholder = painterResource(R.drawable.ic_healing_black_36dp),
                error = painterResource(R.drawable.ic_healing_black_36dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(text = teamName, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }

    @Composable
    fun Score(goalHome: Int, goalGuest: Int, modifier: Modifier){
        Row(modifier = modifier) {
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(modifier = Modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
                Text(text = "$goalHome", modifier = Modifier
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())
                Text(text = "$goalGuest", modifier = Modifier
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}