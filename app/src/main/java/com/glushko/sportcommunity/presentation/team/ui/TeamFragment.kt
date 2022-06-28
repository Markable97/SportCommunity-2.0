package com.glushko.sportcommunity.presentation.team.ui

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity

class TeamFragment: Fragment() {

    private val args: TeamFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? MainActivity)?.setToolbarTitle(args.teamName)
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenTeam()
                }
            }
        }
    }

    @Composable
    private fun ScreenTeam() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
//            TestElements()
//            FooterTeam()
            Button(onClick = { /*TODO*/ }) {
                
            }
            Button(onClick = { /*TODO*/ }) {
                
            }
        }
    }

    @Composable
    fun TestElements() {
        Column()
        {
            Box(modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .weight(1f))
            Box(modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth()
                .weight(3f))
            Box(modifier = Modifier
                .background(Color.Green)
                .fillMaxWidth()
                .weight(2f))
        }
    }

    @Preview
    @Composable
    private fun FooterTeam(){
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            ) {
            Box(modifier = Modifier
                .height(150.dp)
                .background(Color.Red)
                .fillMaxWidth()){
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp, 0.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = getString(R.string.team_btn_results))
                }
            }
            Box(modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {
                    Button(
                        modifier = Modifier
                            .weight(1F)
                            .padding(16.dp, 0.dp, 4.dp, 0.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(text = getString(R.string.team_btn_tournament_table))
                    }
                    Button(
                        modifier = Modifier
                            .weight(1F)
                            .padding(4.dp, 0.dp, 16.dp, 0.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(text = getString(R.string.team_btn_squad))
                    }
                }
            }
            Box(modifier = Modifier
                .background(Color.Green)
                .fillMaxWidth()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = getString(R.string.team_btn_calendar))
                }
            }
        }
    }
}