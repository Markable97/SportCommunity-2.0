package com.glushko.sportcommunity.presentation.admin.schedule.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.databinding.FragmentScheduleBinding
import com.glushko.sportcommunity.databinding.FragmentScheduleCreateBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.ChooseDialog
import com.glushko.sportcommunity.util.Result
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateScheduleFragment: BaseXmlFragment<FragmentScheduleCreateBinding>(R.layout.fragment_schedule_create) {

    private val viewModel: CreateScheduleViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleCreateBinding = FragmentScheduleCreateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListener()
        setupObservers()
    }

    private fun setupObservers() = viewModel.run {
        liveDataStadiums.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {

                }
            }
        }
    }

    private fun setupListener() = binding.run {
        layoutSelectData.root.setOnClickListener {
            showDateDialog()
        }
        layoutSelectTime.root.setOnClickListener {
            showTimeDialog()
        }
        layoutSelectStadium.root.setOnClickListener {
            openChooseDialog(ChooseDialog.prepareBundle(
                binding.layoutSelectStadium.textTitle.text.toString(),
                viewModel.getStadiums()
            )){ data ->
                data?.let {
                    viewModel.selectStadium(it)
                    binding.layoutSelectStadium.textSubtitle.text = it.valueDisplay
                }
            }
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