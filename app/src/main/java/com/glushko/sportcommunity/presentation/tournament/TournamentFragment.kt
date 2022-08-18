package com.glushko.sportcommunity.presentation.tournament

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentTournamentBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class TournamentFragment: BaseXmlFragment<FragmentTournamentBinding>(R.layout.fragment_tournament) {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTournamentBinding = FragmentTournamentBinding.inflate(inflater)
}