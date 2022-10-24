package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.databinding.DialogScheduleMatchesSelectBinding
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment

class MatchesSelectBottomSheetDialog: BaseBottomSheetDialogFragment<DialogScheduleMatchesSelectBinding>() {

    private val args: MatchesSelectBottomSheetDialogArgs by navArgs()

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
    }

    private fun initViews() = binding.run {
        textStadiumName.text = args.stadium.name
        textGameDate.text = "${args.time.date} ${args.time.time}"
    }

}