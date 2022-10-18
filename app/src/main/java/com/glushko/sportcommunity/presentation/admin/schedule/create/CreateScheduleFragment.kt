package com.glushko.sportcommunity.presentation.admin.schedule.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.core.view.isVisible
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentScheduleBinding
import com.glushko.sportcommunity.databinding.FragmentScheduleCreateBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CreateScheduleFragment: BaseXmlFragment<FragmentScheduleCreateBinding>(R.layout.fragment_schedule_create) {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleCreateBinding = FragmentScheduleCreateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListener()
    }

    private fun setupListener() = binding.run {
        layoutSelectData.root.setOnClickListener {
            showDateDialog()
        }
        layoutSelectTime.root.setOnClickListener {
            showTimeDialog()
        }
        layoutSelectStadium.root.setOnClickListener {

        }
        switchHalfBreak.setOnCheckedChangeListener { _, isChecked ->
            textInputHalfBreakTime.isVisible = isChecked
        }
        switchBetweenBreak.setOnCheckedChangeListener { _, isChecked ->
            textInputBreakBetweenTime.isVisible = isChecked
        }
    }

    private fun initViews() = binding.run {
        layoutSelectData.textTitle.text = getString(R.string.schedule_create__date)
        layoutSelectTime.textTitle.text = getString(R.string.schedule_create__game_start)
        layoutSelectStadium.textTitle.text = getString(R.string.schedule_create__stadium)
    }

    private fun showDateDialog() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val datePicker = builder.build()
        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
            val date = dateFormat.format(Date(it))
            binding.layoutSelectData.textSubtitle.text = date
        }
    }

    private fun showTimeDialog(){
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
        timePicker.show(parentFragmentManager, "timePicker")
        timePicker.addOnPositiveButtonClickListener {
            val time = "${timePicker.hour}:${timePicker.minute}"
            binding.layoutSelectTime.textSubtitle.text = time
        }

    }


}