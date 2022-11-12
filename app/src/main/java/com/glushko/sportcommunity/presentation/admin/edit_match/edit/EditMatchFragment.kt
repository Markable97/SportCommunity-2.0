package com.glushko.sportcommunity.presentation.admin.edit_match.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.databinding.FragmentMatchEditBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.admin.edit_match.edit.adapters.ActionsAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.ChooseDialog
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber

class EditMatchFragment: BaseXmlFragment<FragmentMatchEditBinding>(R.layout.fragment_match_edit) {

    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    private val actionAdapter by lazy {
        ActionsAdapter(
            onClickButtonDelete = { position ->
                viewModel.deleteAction(position)
            },
            onClickButtonSave = { position ->
                viewModel.saveAction(position)
            },
            onClickButtonEdit = { position ->
                viewModel.editPlayer(position)
            },
            onClickAction = { position ->
                openChooseDialog(
                    ChooseDialog.prepareBundle(
                        title = getString(R.string.edit_match__edit_action),
                        data = viewModel.getActions()
                    )
                ){ dataReturn ->
                    if (dataReturn != null) {
                        viewModel.setActionToPlayer(dataReturn, position)
                    }

                }
            },
            onClickTime = { position, time ->
                findNavController().navigate(EditMatchFragmentDirections.actionEditMatchToTimeSelectDialog(position, time))
            },
            onClickPlayer = {position ->
                findNavController().navigate(EditMatchFragmentDirections.actionEditMatchToPlayerSelectDialog(position))
            },
            onClickPlayerAssistant = {position ->
                findNavController().navigate(EditMatchFragmentDirections.actionEditMatchToPlayerSelectDialog(position, true))
            }
        )
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
        buttonUpdate.setSafeOnClickListener {
            with(layoutHeader){
                val goalsHome = editGoalsHome.text.toString()
                val goalsGuest = editGoalsGuest.text.toString()
                if (goalsHome.isNotBlank()
                    && goalsGuest.isNotBlank() ) {
                    displayWarning(goalsHome, goalsGuest)
                } else {
                    snackbar(binding.root, getString(R.string.edit_match__empty_score))
                }
            }
        }
    }

    private fun displayWarning(goalsHome: String, goalsGuest: String){
        if (viewModel.liveDataSelectedMatch.value?.isSaved == true) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.edit_match__warning_title))
                .setMessage(getString(R.string.edit_match__warning_message))
                .setPositiveButton(getString(R.string.yes)){ _, _ ->
                    viewModel.clearGoals()
                    viewModel.createOrClearResult(
                        goalsHome,
                        goalsGuest
                    )
                }
                .setNegativeButton(getString(R.string.no)){ _, _ ->
                }
                .show()
        } else {
            viewModel.createOrClearResult(
                goalsHome,
                goalsGuest
            )
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
        eventAddAction.observe(viewLifecycleOwner){ position ->
            actionAdapter.notifyItemInserted(position)
            binding.recyclerActions.scrollToPosition(position - 1)
        }
        eventDeleteAction.observe(viewLifecycleOwner) { position ->
            actionAdapter.notifyItemRemoved(position)
        }
        eventUpdateAction.observe(viewLifecycleOwner) { position ->
            actionAdapter.notifyItemChanged(position)
        }
        eventChangeUpdateButtonText.observe(viewLifecycleOwner){ text ->
            binding.buttonUpdate.text = getString(text)
        }
        eventEnableScore.observe(viewLifecycleOwner){isEnable ->
            enableScore(!isEnable)
            binding.floatingButtonCreateAction.isVisible = isEnable
        }
        eventSaveResult.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {
                    toastLong(requireContext(), it.exception.message?:"error")
                }
                Result.Loading -> {}
                is Result.Success -> {
                    toastLong(requireContext(), it.data)
                }
            }
        }
    }

    private fun enableScore(isEnable: Boolean) = binding.layoutHeader.run {
        editGoalsHome.isEnabled = isEnable
        editGoalsGuest.isEnabled = isEnable
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
    }
}