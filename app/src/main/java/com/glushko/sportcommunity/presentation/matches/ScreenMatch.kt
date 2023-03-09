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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.matches.results.Score
import com.glushko.sportcommunity.util.Constants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardMatch(match: MatchFootballDisplayData, navController: NavController, action: NavDirections?){
    Card(
        modifier = Modifier
            .padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.LightGray,
        onClick = {
            if (match.played == Constants.TYPE_MATCH_PLAYED){
                if (action != null) {
                    navController.navigate(action)
                }
            }
        }
    ){
        Column{
            ResultHeader("${match.leagueName} | ${match.divisionName}")
            ResultHeader(stringResource(id = R.string.match_time, match.matchDate?:"Не назначено"))
            ResultFooter(stringResource(id = R.string.match_stadium, match.stadium?:"Не назначено"))
            Row(
                modifier = Modifier.fillMaxHeight().padding(bottom = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                Teams(match, modifierTeams)
                val modifierScore= if(match.played == 1){
                    Modifier
                        .weight(0.1f)
                }else{
                    Modifier
                        .weight(0.25f)
                }
                if(match.played==Constants.TYPE_MATCH_PLAYED){
                    Score(match.teamHomeGoal, match.teamGuestGoal, modifierScore)
                }
            }
        }
    }
}


@Composable
fun ResultHeader(description: String) {
    Column(modifier = Modifier.padding(top = 5.dp)) {
        Text(text = description,
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
fun Teams(match: MatchFootballDisplayData, modifier: Modifier){
    Column(modifier = modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
        Team(teamName = match.teamHomeName, teamImage = match.teamHomeImage?:"${Constants.BASE_URL_IMAGE}${match.teamHomeName}.png")
        Divider(color = Color.Gray, modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Team(teamName = match.teamGuestName, teamImage = match.teamGuestImage?:"${Constants.BASE_URL_IMAGE}${match.teamGuestName}.png")
    }
}

@Composable
fun Team(teamName: String, teamImage: String){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier.size(48.dp),
            model = teamImage,
            error = painterResource(R.drawable.ic_healing_black_36dp),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(text = teamName, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp), textAlign = TextAlign.Start)
    }
}