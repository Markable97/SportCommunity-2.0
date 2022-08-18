package com.glushko.sportcommunity.presentation.tournament_table.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.util.Constants.BASE_URL_IMAGE
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TournamentTableFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenTournamentTable()
                }
            }
        }
    }

    @Composable
    fun ScreenTournamentTable(){
        val response: Resource<List<TournamentTableDisplayData>> by mainViewModel.liveDataTable.observeAsState(Resource.Empty())
        CreateScreen(response)
    }

    @Composable
    private fun CreateScreen(response: Resource<List<TournamentTableDisplayData>>) {
        Column() {
            when(response){
                is Resource.Error -> {
                    Timber.e(response.error?.message)
                    DoSomething(message = response.error?.message?:"", textButton = "Повторить"){
                        mainViewModel.getTournamentTableRetry()
                    }
                }
                is Resource.Loading -> {
                    Loader()
                }
                is Resource.Success -> {
                    CreateTable(response = response.data!!)
                }
                is Resource.Empty -> {}
            }
        }
    }

    @Composable
    fun CreateTable(response: List<TournamentTableDisplayData>) {
        Timber.d("Отрисовка")
        Column(modifier = Modifier.fillMaxHeight()) {
            val modifierName = Modifier
                .wrapContentHeight()
                .weight(8f)
            val modifierOther = Modifier
                .wrapContentHeight()
                .weight(2f)
            TableHead(modifierName, modifierOther)
            TableBody(response, modifierName, modifierOther)
        }
    }


    @Composable
    fun TableHead(modifier: Modifier, modifierOther: Modifier){
        Row(modifier = Modifier
            .border(1.dp, Color.LightGray)
            .fillMaxWidth()
            .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ){
            TableCell(modifier = modifierOther, value = "#", isHead = true)
            TableCell(modifier = modifierOther, value = "Лого", isHead = true)
            TableCell(modifier = modifier, value = "Команда", isHead = true)
            TableCell(modifier = modifierOther, value = "И", isHead = true)
            TableCell(modifier = modifierOther, value = "В", isHead = true)
            TableCell(modifier = modifierOther, value = "Н", isHead = true)
            TableCell(modifier = modifierOther, value = "П", isHead = true)
            TableCell(modifier = modifierOther, value = "Р/М", isHead = true)
            TableCell(modifier = modifierOther, value = "О", isHead = true)
        }
    }

    @Composable
    fun TableBody(table: List<TournamentTableDisplayData>, modifier: Modifier, modifierOther: Modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            itemsIndexed(table){ num, team ->
                TableRow(num = num + 1, team = team, modifier = modifier, modifierOther = modifierOther)
            }
        }
    }
    @Composable
    fun TableRow(num: Int, team: TournamentTableDisplayData, modifier: Modifier, modifierOther: Modifier){
        Row(modifier = Modifier
            .border(1.dp, Color.LightGray)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    findNavController().navigate(
                        TournamentTableFragmentDirections.actionTournamentTableFragmentToTeamFragment(
                            teamName = team.teamName
                        )
                    )
                }
            ),
            verticalAlignment = Alignment.CenterVertically,

        ){
            TableCell(modifier = modifierOther, value = num.toString())
            TableCellIMage(modifier = modifierOther, value = team.teamName)
            TableCell(modifier = modifier, value = team.teamName)
            TableCell(modifier = modifierOther, value = team.games.toString())
            TableCell(modifier = modifierOther, value = team.wins.toString())
            TableCell(modifier = modifierOther, value = team.draws.toString())
            TableCell(modifier = modifierOther, value = team.losses.toString())
            TableCell(modifier = modifierOther, value = team.scCon.toString())
            TableCell(modifier = modifierOther, value = team.points.toString())
        }
    }

    @Composable
    fun TableCellIMage(modifier: Modifier, value: String){
        AsyncImage(
            model = "$BASE_URL_IMAGE$value.png",
            placeholder = painterResource(R.drawable.ic_healing_black_36dp),
            error = painterResource(R.drawable.ic_healing_black_36dp),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )
    }

    @Composable
    fun TableCell(modifier: Modifier, value: String, isHead: Boolean = false){
        //Ячейка таблицы
        if(isHead){
            Text(text = value, modifier = modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
        }else{
            Text(text = value, modifier = modifier, textAlign = TextAlign.Center)
        }

    }
}