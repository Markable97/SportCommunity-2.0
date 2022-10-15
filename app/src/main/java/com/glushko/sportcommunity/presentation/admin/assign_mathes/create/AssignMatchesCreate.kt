package com.glushko.sportcommunity.presentation.admin.assign_mathes.create

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesCreateBinding
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesListBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class AssignMatchesCreate: BaseXmlFragment<FragmentAssignMatchesCreateBinding>(R.layout.fragment_assign_matches_create) {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignMatchesCreateBinding = FragmentAssignMatchesCreateBinding.inflate(inflater)

}