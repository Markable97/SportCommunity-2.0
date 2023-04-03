package com.glushko.sportcommunity.presentation.matches.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.EmptyText
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.core.bgMainGradient
import com.glushko.sportcommunity.presentation.matches.CardMatch
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.AndroidEntryPoint

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

    @Composable
    private fun ScreenResult() {
        val response by mainViewModel.liveDataMainScreen.observeAsState(Resource.Empty())
        CreateScreen(response = response)
    }

    @Composable
    private fun CreateScreen(response: Resource<ResponseMainScreen>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = bgMainGradient()
                )
        ) {
            when (response) {
                is Resource.Empty -> {}
                is Resource.Error -> {
                    DoSomething(message = response.error?.message ?: "") {
                        mainViewModel.getResultsRetry()
                    }
                }
                is Resource.Loading -> {
                    Loader()
                }
                is Resource.Success -> {
                    viewModel.init()
                    val matches by viewModel.liveDataResults.observeAsState(emptyList())
                    if (matches.isEmpty()) {
                        EmptyText(textMessage = stringResource(id = R.string.empty_result))
                    } else {
                        ResultsList(matches)
                    }
                }
            }
        }

    }

    @Composable
    private fun ResultsList(matches: List<MatchFootballDisplayData>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(matches) { match ->
                CardMatch(
                    match,
                    findNavController(),
                    ResultsFragmentDirections.actionResultsFragmentToDetailMatchFragment(match)
                )
            }
        }
    }
}

@Composable
fun Score(goalHome: Int, goalGuest: Int, modifier: Modifier) {
    Row(
        modifier = modifier,
    ) {
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "$goalHome",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
            )
            Text(
                text = "$goalGuest", modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
