package com.glushko.sportcommunity.presentation.admin.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAdminBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class AdminFragment: BaseXmlFragment<FragmentAdminBinding>(R.layout.fragment_admin) {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminBinding = FragmentAdminBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = binding.run {
        layoutAssignMatches.textItem.text = getString(R.string.admin_menu__assign_matches)
        layoutSchedule.textItem.text = getString(R.string.admin_menu__schedule)
    }
}