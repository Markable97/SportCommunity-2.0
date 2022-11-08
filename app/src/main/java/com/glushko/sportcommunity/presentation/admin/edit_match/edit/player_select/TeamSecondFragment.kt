package com.glushko.sportcommunity.presentation.admin.edit_match.edit.player_select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentTeamPlayersBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class TeamSecondFragment: BaseXmlFragment<FragmentTeamPlayersBinding>(R.layout.fragment_team_players) {

    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTeamPlayersBinding = FragmentTeamPlayersBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}