package com.glushko.sportcommunity.presentation.admin.edit_match.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.databinding.FragmentMatchEditBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.admin.edit_match.edit.adapters.ActionsAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import com.glushko.sportcommunity.util.extensions.visible

class EditMatchFragment: BaseXmlFragment<FragmentMatchEditBinding>(R.layout.fragment_match_edit) {

    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    private val actionAdapter by lazy {
        ActionsAdapter()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMatchEditBinding = FragmentMatchEditBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        setupObservers()
        setupListener()
    }

    private fun setupListener() = binding.run {
        floatingButtonCreateAction.setSafeOnClickListener {
            viewModel.addAction()
        }
    }

    private fun initRecycler() {
        binding.recyclerActions.adapter = actionAdapter
    }

    private fun setupObservers() = viewModel.run {
        liveDataSelectedMatch.observe(viewLifecycleOwner){
            if (it != null) {
                initView(it)
            }
        }
        liveDataPlayersWithActions.observe(viewLifecycleOwner){
            actionAdapter.setData(it)
        }
        eventAddAction.observe(viewLifecycleOwner){
            actionAdapter.notifyDataSetChanged()
            binding.recyclerActions.scrollToPosition(it - 1)
        }
    }

    private fun initView(data: MatchUI) = binding.layoutHeader.run {
        textDivision.text = data.tournamentName
        textTour.text = data.tour
        textTeamHome.text = data.teamHomeName
        imageTeamHome.load("${Constants.BASE_URL_IMAGE}${data.teamHomeName}.png")
        textTeamGuest.text = data.teamGuestName
        imageTeamGuest.load("${Constants.BASE_URL_IMAGE}${data.teamGuestName}.png")
        if (data.teamHomeGoals != null ) {
            editGoalsHome.setText(data.teamHomeGoals.toString())
        }
        if (data.teamGuestGoals != null ) {
            editGoalsGuest.setText(data.teamGuestGoals.toString())
        }
        groupScore.visible()
        editGoalsGuest.isEnabled = true
        editGoalsHome.isClickable = true
    }
}