package com.glushko.sportcommunity.presentation.results.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.BaseFragment
import com.glushko.sportcommunity.util.Constants
import timber.log.Timber

class ResultsFragment : BaseFragment() {

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
        }
    }

    @Preview
    @Composable
    fun ScreenResult() {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            items(10){
                CardResult()
            }
        }
    }

    @Composable
    fun CardResult(){
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
                ResultHeader()
                Row() {
                    val modifierTour= Modifier
                        .weight(0.1f)
                    TextTour(modifier = modifierTour)
                    val modifierTeams= Modifier
                        .weight(0.8f)
                    Teams(modifierTeams)
                    val modifierScore= Modifier
                        .weight(0.1f)
                    Score(modifierScore)
                }
            }
        }
    }

    @Composable
    fun ResultHeader() {
        Column {
            Text(text = "Первый дивизион",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Divider(color = Color.Gray)
        }
    }

    @Composable
    fun TextTour(modifier: Modifier){
        Box(contentAlignment = Alignment.Center, modifier =  modifier.fillMaxHeight()) {
            Text(text = "10 тур", textAlign = TextAlign.Center)
        }

    }

    @Composable
    fun Teams(modifier: Modifier){
        Column(modifier = modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
            Team(teamName = "Косино")
            Spacer(modifier = Modifier.height(1.dp))
            Team(teamName = "Морс")
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
                placeholder = painterResource(R.drawable.ic_healing_black_36dp),
                error = painterResource(R.drawable.ic_healing_black_36dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(text = teamName, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }

    @Composable
    fun Score(modifier: Modifier){
        Row(modifier = modifier) {
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(modifier = Modifier.fillMaxHeight(),verticalArrangement  = Arrangement.SpaceEvenly) {
                Text(text = "5", modifier = Modifier
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())
                Text(text = "2", modifier = Modifier
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}