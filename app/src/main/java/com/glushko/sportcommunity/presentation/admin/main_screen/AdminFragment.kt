package com.glushko.sportcommunity.presentation.admin.main_screen

import android.app.DirectAction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAdminBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity

class AdminFragment: BaseXmlFragment<FragmentAdminBinding>(R.layout.fragment_admin) {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminBinding = FragmentAdminBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListeners()
    }

    private fun setupListeners() = binding.run {
        layoutAssignMatches.root.setOnClickListener {
            navigateMenu(AdminFragmentDirections.actionAdminFragmentToAssignMatchesFragment(), R.string.admin_menu__assign_matches)
        }
        layoutSchedule.root.setOnClickListener {
            navigateMenu(AdminFragmentDirections.actionAdminFragmentToScheduleFragment(), R.string.admin_menu__schedule)
        }
    }

    private fun navigateMenu(action: NavDirections, @StringRes title: Int){
        (requireActivity() as? MainActivity)?.setToolbarTitle(getString(title))
        findNavController().navigate(action)
    }

    private fun initViews() = binding.run {
        layoutAssignMatches.textItem.text = getString(R.string.admin_menu__assign_matches)
        layoutSchedule.textItem.text = getString(R.string.admin_menu__schedule)
    }
}