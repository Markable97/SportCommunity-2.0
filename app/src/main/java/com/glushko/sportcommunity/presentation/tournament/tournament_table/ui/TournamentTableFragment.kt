package com.glushko.sportcommunity.presentation.tournament.tournament_table.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.bgMainGradient
import com.glushko.sportcommunity.presentation.tournament.TournamentViewModel
import com.glushko.sportcommunity.util.Constants.BASE_URL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentTableFragment : BaseFragment() {

    companion object {
        private const val BORDER_SIZE = 0.5
    }

    private val viewModel: TournamentViewModel by hiltNavGraphViewModels(R.id.nav_graph_tournament)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setContent {
                Surface() {
                    ScreenTournamentTable()
                }
            }
        }
    }

    @Composable
    fun ScreenTournamentTable() {
        val response: List<TournamentTableDisplayData> by viewModel.liveDataTable.observeAsState(
            emptyList()
        )
        CreateScreen(response)
    }

    @Composable
    private fun CreateScreen(response: List<TournamentTableDisplayData>) {
        Column(
            modifier = Modifier.background(
                brush = bgMainGradient()
            )
        ) {
            CreateTable(
                response = response
            ) { team ->
                findNavController().navigate(
                    TournamentTableFragmentDirections.actionTournamentTableFragmentToTeamFragment(
                        teamName = team.teamName,
                        teamId = team.teamId,
                        teamImage = team.teamImage
                    )
                )
            }
        }
    }
}

@Composable
fun CreateTable(
    response: List<TournamentTableDisplayData>,
    positionColor: Boolean = true,
    onClickTeam: (TournamentTableDisplayData) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        val modifierName = Modifier
            .wrapContentHeight()
            .weight(8f)
        val modifierOther = Modifier
            .wrapContentHeight()
            .weight(2f)
        TableHead(modifierName, modifierOther)
        TableBody(onClickTeam, response, modifierName, modifierOther, positionColor)
    }
}

@Composable
fun CreateTableWidget(
    response: List<TournamentTableDisplayData>,
    positionColor: Boolean = true,
    teamIdSelect: Int? = null,
    onClickTeam: (TournamentTableDisplayData) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        val modifierName = Modifier
            .wrapContentHeight()
            .weight(8f)
        val modifierOther = Modifier
            .wrapContentHeight()
            .weight(2f)
        TableHead(modifierName, modifierOther)
        TableBodyWidget(onClickTeam, response, modifierName, modifierOther, positionColor, teamIdSelect)
    }
}


@Composable
fun TableHead(modifier: Modifier, modifierOther: Modifier) {
    Row(
        modifier = Modifier
//            .border(BORDER_SIZE.dp, colorResource(id = R.color.primary_color))
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
fun TableBody(
    onClickTeam: (TournamentTableDisplayData) -> Unit,
    table: List<TournamentTableDisplayData>, modifier: Modifier, modifierOther: Modifier, positionColor: Boolean
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(table) { num, team ->
            TableRow(
                onClickTeam = onClickTeam,
                num = num + 1,
                team = team,
                modifier = modifier,
                modifierOther = modifierOther,
                positionColor
            )
        }
    }
}@Composable
fun TableBodyWidget(
    onClickTeam: (TournamentTableDisplayData) -> Unit,
    table: List<TournamentTableDisplayData>,
    modifier: Modifier,
    modifierOther: Modifier,
    positionColor: Boolean,
    teamIdSelected: Int?,
) {
    table.forEachIndexed { num, team ->
        TableRow(
            onClickTeam = onClickTeam,
            num = team.position,
            team = team,
            modifier = modifier,
            modifierOther = modifierOther,
            positionColor = positionColor,
            teamIdSelected = teamIdSelected
        )
    }
}

@Composable
fun TableRow(
    onClickTeam: (TournamentTableDisplayData) -> Unit,
    num: Int,
    team: TournamentTableDisplayData,
    modifier: Modifier,
    modifierOther: Modifier,
    positionColor: Boolean,
    teamIdSelected: Int? = null
) {
    val colorPosition = if (positionColor){
        if (team.positionColor != null) {
            Color(android.graphics.Color.parseColor(team.positionColor))
        } else {
            Color.Transparent
        }
    } else {
        Color.Transparent
    }
    val (fontSize, fontWeight) = if (team.teamId == teamIdSelected) {
        16.sp to FontWeight.Bold
    } else {
        14.sp to FontWeight.Normal
    }
    Row(
        modifier = Modifier
//            .border(BORDER_SIZE.dp, colorResource(id = R.color.primary_color))
            .fillMaxWidth()
            .background(colorPosition)
            .wrapContentHeight()
            .clickable(
                onClick = {
                    onClickTeam.invoke(team)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        TableCell(modifier = modifierOther, value = num.toString(), isHead = false, fontSize, fontWeight)
        TableCellIMage(modifier = modifierOther, value = team.teamImage ?: "${BASE_URL_IMAGE}${team.teamName}.png")
        TableCell(modifier = modifier, value = team.teamName, fontSize = fontSize, fontWeight = fontWeight)
        TableCell(modifier = modifierOther, value = team.games.toString(), fontSize = fontSize, fontWeight = fontWeight)
        TableCell(modifier = modifierOther, value = team.wins.toString(), fontSize = fontSize, fontWeight = fontWeight)
        TableCell(modifier = modifierOther, value = team.draws.toString(), fontSize = fontSize, fontWeight = fontWeight)
        TableCell(modifier = modifierOther, value = team.losses.toString(), fontSize = fontSize, fontWeight = fontWeight)
        TableCell(modifier = modifierOther, value = team.scCon.toString(), fontSize = fontSize, fontWeight = fontWeight)
        TableCell(modifier = modifierOther, value = team.points.toString(), fontSize = fontSize, fontWeight = fontWeight)
    }
}

@Composable
fun TableCellIMage(modifier: Modifier, value: String) {
    AsyncImage(
        model = value,
        placeholder = painterResource(R.drawable.ic_healing_black_36dp),
        error = painterResource(R.drawable.ic_healing_black_36dp),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier.size(48.dp),
    )
}

@Composable
fun TableCell(modifier: Modifier, value: String, isHead: Boolean = false, fontSize: TextUnit = 14.sp, fontWeight: FontWeight = FontWeight.Normal) {
    //Ячейка таблицы
    if (isHead) {
        Text(text = value, modifier = modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
    } else {
        Text(text = value, modifier = modifier, textAlign = TextAlign.Center, fontWeight = fontWeight, fontSize = fontSize)
    }
}