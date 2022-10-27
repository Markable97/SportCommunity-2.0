package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.glushko.sportcommunity.databinding.DialogScheduleMatchViewBinding
import com.glushko.sportcommunity.presentation.admin.schedule.ScheduleFragment
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchViewBottomSheetDialog : BaseBottomSheetDialogFragment<DialogScheduleMatchViewBinding>() {


    private val args: MatchViewBottomSheetDialogArgs by navArgs()

    private val viewModel: MatchViewViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogScheduleMatchViewBinding = DialogScheduleMatchViewBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.eventDeletedMatch.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    val matchBackUp = args.match.match?.copy()
                    args.match.apply { match = null }
                    parentFragmentManager.setFragmentResult(
                        ScheduleFragment.REQUEST_CODE,
                        bundleOf(
                            ScheduleFragment.BUNDLE_POSITION to args.position,
                            ScheduleFragment.BUNDLE_DELETE to true,
                            ScheduleFragment.BUNDLE_MATCH to matchBackUp
                        )
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.run {
            buttonDelete.setSafeOnClickListener {
                viewModel.deleteMatch(args.stadium, args.match)
            }
            imageClose.setSafeOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initView() = binding.run {
        textStadiumName.text = args.stadium.name
        textTournamentName.text = args.match.match?.tournamentName
        textTour.text = args.match.match?.tour
        textGameDate.text = "${args.match.date}\n${args.match.time}"
        textTeamHome.text = args.match.match?.teamHomeName
        imageTeamHome.load("")
        textTeamGuest.text = args.match.match?.teamGuestName
        imageTeamGuest.load("")
    }
}