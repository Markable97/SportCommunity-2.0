package com.glushko.sportcommunity.presentation.matches

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.matches.calendar.ui.DateAndStadium
import com.glushko.sportcommunity.presentation.matches.results.ui.ResultsFragmentDirections
import com.glushko.sportcommunity.presentation.matches.results.ui.Score
import com.glushko.sportcommunity.util.Constants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardMatch(match: MatchFootballDisplayData, navController: NavController){
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.LightGray,
        onClick = {
            if (match.played == 1){
                navController.navigate(ResultsFragmentDirections.actionResultsFragmentToDetailMatchFragment(match))
            }
        }
    ){
        Column{
            ResultHeader(match.matchDate?:"14 Сентября 2022 (ПН)") //TODO проверит как приходит с сервера
            ResultFooter(match.stadium?:"Не назначено") //TODO проверит как приходит с сервера
            Row(modifier = Modifier.padding(bottom = 10.dp, end = 10.dp)) {
                val modifierTour= Modifier
                    .weight(0.1f)
                TextTour(tour = match.tour,modifier = modifierTour)
                Spacer(modifier = Modifier.width(10.dp))
                val modifierTeams= if(match.played == 1){
                    Modifier
                        .weight(0.8f)
                }else{
                    Modifier
                        .weight(0.65f)
                }
                Teams(teamHome = match.teamHomeName, teamGuest = match.teamGuestName, modifierTeams)
                val modifierScore= if(match.played == 1){
                    Modifier
                        .weight(0.1f)
                }else{
                    Modifier
                        .weight(0.25f)
                }
                if(match.played==1){
                    Score(match.teamHomeGoal, match.teamGuestGoal,modifierScore)
                }
            }
        }
    }
}


@Composable
fun ResultHeader(date: String) {
    Column(modifier = Modifier.padding(top = 5.dp)) {
        Text(text = date,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ResultFooter(stadium: String) {
    Row() {
        Text(text = stadium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun TextTour(tour: String, modifier: Modifier){
    Box(contentAlignment = Alignment.Center, modifier =  modifier.fillMaxHeight()) {
        Text(text = tour, textAlign = TextAlign.Center)
    }

}

@Composable
fun Teams(teamHome: String, teamGuest: String, modifier: Modifier){
    Column(modifier = modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
        Team(teamName = teamHome)
        Divider(color = Color.Gray, modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
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
            modifier = Modifier.size(48.dp),
            model = "${Constants.BASE_URL_IMAGE}$teamName.png",
            error = painterResource(R.drawable.ic_healing_black_36dp),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(text = teamName, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp), textAlign = TextAlign.Start)
    }
}