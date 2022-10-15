package com.glushko.sportcommunity.presentation.admin.assign_mathes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class AssignMatchesFragment: BaseXmlFragment<FragmentAssignMatchesBinding>(R.layout.fragment_assign_matches) {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignMatchesBinding = FragmentAssignMatchesBinding.inflate(inflater)
}