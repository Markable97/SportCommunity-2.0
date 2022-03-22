package com.glushko.sportcommunity.presentation.match_detail.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
import com.glushko.sportcommunity.data.results.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.match_detail.vm.DetailMatchViewModel
import com.glushko.sportcommunity.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailMatchFragment: Fragment() {
    private val viewModel: DetailMatchViewModel by viewModels()
    private val args: DetailMatchFragmentArgs by navArgs()
    private val match by lazy { args.match }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("Матч инфо = $match")
        viewModel.getPlayersInMatch(match.matchId)
        return ComposeView(requireContext()).apply {
            setContent {
                DetailMatchMainScreen()
            }
        }
    }

    @Composable
    fun DetailMatchMainScreen(){
        val playersInMatch by viewModel.liveDataPlayersInMatch.observeAsState(initial = emptyList())
        Column {
            UpperCardMatch(match)
            ActionsTeams(match, playersInMatch)
        }
    }

    @Composable
    fun UpperCardMatch(match: MatchFootballDisplayData){
        Card(
            elevation = 10.dp,
            backgroundColor = Color(0xFFFA4659),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 5.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                //.wrapContentHeight()
            ){
                Text(
                    text = "${match.leagueName} | ${match.divisionName}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val modifierTeam = Modifier
                        .weight(2f)
                        .padding(bottom = 5.dp)
                    val modifierScore = Modifier
                        .weight(1f)
                    TeamInMatch(match.teamHomeName, modifierTeam)
                    TourAndScore(
                        tour = match.tour,
                        goalHome = match.teamHomeGoal,
                        goalGuest = match.teamGuestGoal,
                        modifier = modifierScore
                    )
                    TeamInMatch(match.teamGuestName, modifierTeam)
                }
            }
        }
    }

    @Composable
    fun TeamInMatch(teamName: String, modifier: Modifier){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            AsyncImage(
                model = "${Constants.BASE_URL_IMAGE}$teamName.png",
                error = painterResource(R.drawable.ic_healing_black_36dp),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Text(
                text = teamName,
                textAlign = TextAlign.Center,
                softWrap = true,
                maxLines = 2
            )
        }
    }

    @Composable
    fun TourAndScore(tour: Int, goalHome: Int, goalGuest: Int, modifier: Modifier){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ){
            Text(
                text = "Тур $tour",
                modifier = Modifier
                    .padding(top = 5.dp)
                //.wrapContentHeight()
                //.weight(1f)
                ,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$goalHome : $goalGuest",
                modifier = Modifier
                    .padding(top = 15.dp)
                //.wrapContentHeight()
                //.weight(2f)
                ,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Composable
    fun ActionsTeams(match: MatchFootballDisplayData, playersInMatch: List<PlayerDisplayData>){
        Row(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(all = 5.dp)) {
            if(playersInMatch.isNotEmpty()){
                val modifier = Modifier
                    .weight(1f)
                    .padding(all = 5.dp)

                ListActionsPlayers(players = playersInMatch.filter{it.teamName == match.teamHomeName}, modifier)
                Box(modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray))
                ListActionsPlayers(players = playersInMatch.filter{it.teamName == match.teamGuestName}, modifier)
            }
        }
    }

    @Composable
    fun ListActionsPlayers(players: List<PlayerDisplayData>, modifier: Modifier) {
        var assists = ""
        LazyColumn(modifier = modifier) {
            for(player in players){
                if (player.goal > 0) item { ItemInfo(typeAction = "goal", playerName = "${player.playerName} (${player.goal})") }
                if (player.penalty > 0) item { ItemInfo(typeAction = "penalty", playerName = player.playerName) }
                if (player.penaltyOut > 0) item { ItemInfo(typeAction = "penalty out", playerName = player.playerName) }
                if (player.ownGoal > 0) item { ItemInfo(typeAction = "own goal", playerName = player.playerName) }
                if (player.yellow > 0) item { ItemInfo(typeAction = if(player.yellow == 1) "yellow card" else "two yellow", playerName = player.playerName) }
                if (player.red > 0) item { ItemInfo(typeAction = "red card", playerName = player.playerName) }
                if (player.assist > 0) assists = "${player.playerName} (${player.assist}), "
            }
            item { Text(text = "Ассистенты: $assists") }
        }

    }

    data class Result(val image: Painter, val description: String)
    @Composable
    fun ItemInfo(typeAction: String, playerName: String){
        Row {
            val modifierText = Modifier.weight(2f)
            val modifierImage = Modifier.weight(1f)
            Text(text = playerName, modifier = modifierText)
            val (image,description) = when(typeAction) {
                "goal" -> Result(painterResource(id = R.drawable.goal), typeAction)
                "penalty" -> Result(painterResource(id = R.drawable.penalty), typeAction)
                "penalty out" -> Result(painterResource(id = R.drawable.penalty_out), typeAction)
                "own goal" -> Result(painterResource(id = R.drawable.own_goal), typeAction)
                "yellow card" -> Result(painterResource(id = R.drawable.yellow_card), typeAction)
                "red card" -> Result(painterResource(id = R.drawable.red_card), typeAction)
                "two yellow" -> Result(painterResource(id = R.drawable.red_yellow_card), typeAction)
                else -> Result(painterResource(id = R.drawable.ic_healing_black_36dp), "no image")
            }
            Image(painter = image, contentDescription = description, modifier = modifierImage)
        }
    }
}