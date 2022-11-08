package com.glushko.sportcommunity.presentation.admin.edit_match.edit.player_select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.DialogPlayerSelectBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.admin.edit_match.edit.adapters.PlayersOfTeamsTabAdapters
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class PlayerSelectDialog: BaseBottomSheetDialogFragment<DialogPlayerSelectBinding>() {

    private val args: PlayerSelectDialogArgs by navArgs()
    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogPlayerSelectBinding = DialogPlayerSelectBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMaxHeight(0.9)
        setupViewPager()
        initTitle()
    }

    private fun initTitle() = binding.run {
        textTitle.text = getString(
            if (args.isAssistant) R.string.edit_match__edit_player_assistant_title else R.string.edit_match__edit_player_title
        )
    }

    private fun setupViewPager() = binding.run {
        viewPager.adapter = PlayersOfTeamsTabAdapters(this@PlayerSelectDialog)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val title = when(position){
                PlayersOfTeamsTabAdapters.TAB_TEAM_FIRST -> viewModel.liveDataSelectedMatch.value?.teamHomeName
                else -> viewModel.liveDataSelectedMatch.value?.teamGuestName
            }
            tab.text = title
        }.attach()
    }
}