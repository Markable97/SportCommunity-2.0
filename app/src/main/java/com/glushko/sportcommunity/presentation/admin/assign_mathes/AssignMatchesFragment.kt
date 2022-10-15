package com.glushko.sportcommunity.presentation.admin.assign_mathes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesBinding
import com.glushko.sportcommunity.presentation.admin.assign_mathes.adapters.AssignMatchesTabAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignMatchesFragment: BaseXmlFragment<FragmentAssignMatchesBinding>(R.layout.fragment_assign_matches) {

    private val viewModel: AssignMatchesViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignMatchesBinding = FragmentAssignMatchesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }
    private fun setupViewPager() = binding.run {
        viewPager.adapter = AssignMatchesTabAdapter(this@AssignMatchesFragment)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val title = AssignMatchesTabAdapter.getPageTitle(position)
            tab.setText(title)
        }.attach()
    }
}