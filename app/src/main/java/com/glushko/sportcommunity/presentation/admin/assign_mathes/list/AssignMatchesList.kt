package com.glushko.sportcommunity.presentation.admin.assign_mathes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesListBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class AssignMatchesList: BaseXmlFragment<FragmentAssignMatchesListBinding>(R.layout.fragment_assign_matches_list) {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignMatchesListBinding = FragmentAssignMatchesListBinding.inflate(inflater)

}