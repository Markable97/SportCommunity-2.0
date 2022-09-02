package com.glushko.sportcommunity.presentation.team.squad

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentSquadBinding
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquadFragment: BaseXmlFragment<FragmentSquadBinding>(R.layout.fragment_squad){
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSquadBinding = FragmentSquadBinding.inflate(inflater)

}