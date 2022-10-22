package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.glushko.sportcommunity.databinding.DialogScheduleMatchViewBinding
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment

class MatchViewBottomSheetDialog: BaseBottomSheetDialogFragment<DialogScheduleMatchViewBinding>() {

    private val args: MatchViewBottomSheetDialogArgs by navArgs()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogScheduleMatchViewBinding = DialogScheduleMatchViewBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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