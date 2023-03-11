package com.glushko.sportcommunity.presentation.match_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.match_detail.model.MatchAction
import com.glushko.sportcommunity.data.match_detail.model.MatchSegment
import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.data.match_detail.model.getDrawable
import com.glushko.sportcommunity.databinding.FragmentMatchDetailBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.match_detail.adapters.MatchDetailAdapter
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.matches.model.MatchScreenType
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.gone
import com.glushko.sportcommunity.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MatchDetailFragment: BaseXmlFragment<FragmentMatchDetailBinding>(R.layout.fragment_match_detail) {
    private val viewModel: DetailMatchViewModel by viewModels()
    private val args: MatchDetailFragmentArgs by navArgs()
    private val match: MatchFootballDisplayData by lazy { args.match }

    private val adapterMatchDetail by lazy { MatchDetailAdapter() }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMatchDetailBinding = FragmentMatchDetailBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Матч инфо = $match")
        viewModel.getPlayersInMatch(match.matchId, match.teamHomeId, match.screenType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(match.screenType) {
            MatchScreenType.TIME_LINE -> {
                setupRecycler()
                setupObservers()
                renderHeaderCompose()
            }
            MatchScreenType.LISTING -> {
                binding.recyclerMatchActionDetail.gone()
                renderOnlyCompose()
            }
        }
    }

    private fun renderOnlyCompose() {
        binding.composableMatchHeader.setContent {
            DetailMatchMainScreen()
        }
    }

    private fun renderHeaderCompose() {
        binding.composableMatchHeader.setContent {
            UpperCardMatch(match = match)
        }
    }

    private fun setupRecycler() {
        binding.recyclerMatchActionDetail.visible()
        binding.recyclerMatchActionDetail.adapter = adapterMatchDetail
    }

    private fun setupObservers() = viewModel.run {
        liveDataPlayersInMatch.observe(viewLifecycleOwner){
            Timber.d("Live data $it")
            showProgress(it is Result.Loading)
            when(it){
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    adapterMatchDetail.submitList(it.data)
                }
            }
        }
    }

    @Composable
    fun DetailMatchMainScreen(){
        Column {
            UpperCardMatch(match = match)
            val response by viewModel.liveDataPlayersInMatch.observeAsState(Result.Loading)
            Timber.d("Live data $response")
            CreateScreen(response = response)
        }
    }

    @Composable
    private fun CreateScreen(response: Result<List<PlayerInMatchSegment>>) {
        when (response) {
            is Result.Error -> {
                DoSomething(message = response.exception.message ?: "") {
                    viewModel.getPlayersInMatch(match.matchId, match.teamHomeId, match.screenType)
                }
            }
            is Result.Loading -> {
                Loader()
            }
            is Result.Success -> {
                ActionsTeams(response.data)
            }
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
                .clickable {
                    //TODO navigate to Edit Match for admin
                    //findNavController().navigate(MatchDetailFragmentDirections.actionDetailMatchFragmentToNestedNavigationEditMatches(match.toModelEditMatch()))
                }
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
                    TeamInMatch(match.teamHomeName, modifierTeam, match.teamHomeImage, match.teamHomeId)
                    TourAndScore(
                        tour = match.tour,
                        goalHome = match.teamHomeGoal,
                        goalGuest = match.teamGuestGoal,
                        modifier = modifierScore
                    )
                    TeamInMatch(match.teamGuestName, modifierTeam, match.teamGuestImage, match.teamGuestId)
                }
            }
        }
    }

    @Composable
    fun TeamInMatch(
        teamName: String,
        modifier: Modifier,
        teamImage: String?,
        teamId: Int
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.clickable {
                findNavController().navigate(
                    MatchDetailFragmentDirections.actionDetailMatchFragmentToTeamFragment(
                        teamId = teamId,
                        teamName = teamName,
                        teamImage = teamImage
                    )
                )
            }
        ) {
            AsyncImage(
                model = teamImage ?: "${Constants.BASE_URL_IMAGE}$teamName.png",
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
    fun TourAndScore(tour: String, goalHome: Int, goalGuest: Int, modifier: Modifier){
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
    fun ActionsTeams(playersInMatch: List<PlayerInMatchSegment>){
        Row(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(all = 5.dp)) {
            if(playersInMatch.isNotEmpty()){
                val modifier = Modifier
                    .weight(1f)
                    .padding(all = 5.dp)

                ListActionsPlayers(players = playersInMatch.filter{it.segment == MatchSegment.ACTION_HOME}, modifier)
                Box(modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray))
                ListActionsPlayers(players = playersInMatch.filter{it.segment == MatchSegment.ACTION_GUEST}, modifier)
            }
        }
    }

    @Composable
    fun ListActionsPlayers(players: List<PlayerInMatchSegment>, modifier: Modifier) {
        var assists = ""
        LazyColumn(modifier = modifier) {
            for(player in players){
                val action = player.player?.typeAction ?: continue
                if (action == MatchAction.ASSIST) {
                    assists += "${player.player.playerName}; "
                } else {
                    item {
                        ItemInfo(typeAction = action, playerName = player.player.playerName)
                    }
                }
            }
            item { Text(text = "Ассистенты: $assists") }
        }

    }

    @Composable
    fun ItemInfo(typeAction: MatchAction, playerName: String){
        Row {
            val modifierText = Modifier.weight(2f)
            val modifierImage = Modifier.weight(1f)
            Text(text = playerName, modifier = modifierText)
            val imageAction = typeAction.getDrawable()
            Image(painter = painterResource(id = imageAction), contentDescription = null, modifier = modifierImage)
        }
    }
}