package com.glushko.sportcommunity.presentation.admin.edit_match.protocol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentProtocolBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.admin.edit_match.protocol.adapters.PlayersOfTeamsTabAdapters
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import com.glushko.sportcommunity.util.extensions.toastLong
import com.google.android.material.tabs.TabLayoutMediator

class ProtocolFragment : BaseXmlFragment<FragmentProtocolBinding>(R.layout.fragment_protocol) {

    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProtocolBinding = FragmentProtocolBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupListeners()
        setupObservers()
        toastLong(requireContext(), R.string.edit_match__protocol_warning)
    }

    private fun setupObservers() {
        viewModel.eventSaveResult.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    (requireActivity() as? MainActivity)?.setToolbarTitle( getString(R.string.edit_match))
                    findNavController().navigate(
                        ProtocolFragmentDirections.actionProtocolFragmentToEditMatchFragment()
                    )
                }
            }
        }
    }

    private fun setupListeners() {
        binding.buttonSelect.setSafeOnClickListener {
            if (viewModel.checkPlayersEmptyForProtocol()){
                viewModel.addPlayerInMatch()
            } else {
                toastLong(requireContext(), R.string.edit_match__protocol_empty_team)
            }
        }
    }

    private fun setupViewPager() = binding.run {
        viewPager.adapter = PlayersOfTeamsTabAdapters(this@ProtocolFragment)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val title = when(position){
                PlayersOfTeamsTabAdapters.TAB_TEAM_FIRST -> viewModel.liveDataSelectedMatch.value?.teamHomeName
                else -> viewModel.liveDataSelectedMatch.value?.teamGuestName
            }
            tab.text = title
        }.attach()
    }

}