package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.databinding.DialogScheduleMatchesSelectBinding
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MatchesSelectBottomSheetDialog: BaseBottomSheetDialogFragment<DialogScheduleMatchesSelectBinding>() {

    private val args: MatchesSelectBottomSheetDialogArgs by navArgs()

    private val viewModel: MatchesSelectViewModel by viewModels()

    private val matchesAdapter by lazy{
        MatchesSelectAdapter(
            onClickItem = {
                viewModel.checkButtonAdd(it)
            }
        )
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogScheduleMatchesSelectBinding {
        return DialogScheduleMatchesSelectBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMaxHeight(0.8)
        initViews()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() = binding.run {
        buttonSelect.setSafeOnClickListener {
            viewModel.addMatchInSchedule(
                args.stadium, args.time
            )
        }
    }

    private fun setupObservers() = viewModel.run {
        liveDataAssignedMatches.observe(viewLifecycleOwner){
            matchesAdapter.setData(it)
        }
        liveDataCheckButtonAdd.observe(viewLifecycleOwner){
            binding.buttonSelect.isEnabled = it
        }
        eventSuccessAddedMatch.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    Timber.d(it.data)
                }
            }
        }
    }

    private fun initViews() = binding.run {
        recyclerMatches.adapter = matchesAdapter
        textStadiumName.text = args.stadium.name
        textGameDate.text = "${args.time.date} ${args.time.time}"
    }

}