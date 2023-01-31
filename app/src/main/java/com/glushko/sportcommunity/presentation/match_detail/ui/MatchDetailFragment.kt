package com.glushko.sportcommunity.presentation.match_detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.databinding.FragmentMatchDetailBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.match_detail.adapters.MatchDetailAdapter
import com.glushko.sportcommunity.presentation.match_detail.vm.DetailMatchViewModel
import com.glushko.sportcommunity.util.Constants
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Матч инфо = $match")
        viewModel.getPlayersInMatch(match.matchId, match.teamHomeId)
        setupRecycler()
        setupObservers()
        renderCompose()
    }

    private fun setupRecycler() {
        binding.recyclerMatchActionDetail.adapter = adapterMatchDetail
    }

    private fun renderCompose() {
        binding.composableMatchHeader.setContent {
            DetailMatchMainScreen()
        }
    }

    private fun setupObservers() = viewModel.run {
        liveDataPlayersInMatch.observe(viewLifecycleOwner){
            Timber.d("Live data $it")
            when(it){
                is com.glushko.sportcommunity.util.Result.Error -> {}
                is com.glushko.sportcommunity.util.Result.Loading -> {}
                is com.glushko.sportcommunity.util.Result.Success -> {
                    adapterMatchDetail.submitList(it.data)
                }
            }
        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        Timber.d("Матч инфо = $match")
//        viewModel.getPlayersInMatch(match.match)
//        return ComposeView(requireContext()).apply {
//            setContent {
//                DetailMatchMainScreen()
//            }
//        }
//    }

    @Composable
    fun DetailMatchMainScreen(){
        UpperCardMatch(match = match)
//        val response by viewModel.liveDataPlayersInMatch.observeAsState(Resource.Empty())
//        CreateScreen(response = response)
    }

    /*@Composable
    private fun CreateScreen(response: Resource<List<PlayerDisplayData>>){
        Column {
            UpperCardMatch(match)
            when(response){
                is Resource.Empty -> {}
                is Resource.Error -> {
                    DoSomething(message = response.error?.message?:"", textButton = "Повторить"){
                    viewModel.getPlayersInMatch(match.match, match.teamHomeId)
                }
                }
                is Resource.Loading -> {
                    Loader()
                }
                is Resource.Success -> {
                    ActionsTeams(match, response.data!!)
                }
            }

        }
    }*/

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
                    TeamInMatch(match.teamHomeName, modifierTeam, match.teamHomeImage)
                    TourAndScore(
                        tour = match.tour,
                        goalHome = match.teamHomeGoal,
                        goalGuest = match.teamGuestGoal,
                        modifier = modifierScore
                    )
                    TeamInMatch(match.teamGuestName, modifierTeam, match.teamGuestImage)
                }
            }
        }
    }

    @Composable
    fun TeamInMatch(teamName: String, modifier: Modifier, teamImage: String?){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
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

    /*@Composable
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
    }*/

    /*@Composable
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

    }*/

    data class Result(val image: Painter, val description: String)
   /* @Composable
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
    }*/
}