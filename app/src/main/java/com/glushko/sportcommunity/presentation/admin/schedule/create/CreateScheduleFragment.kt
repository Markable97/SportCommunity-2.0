package com.glushko.sportcommunity.presentation.admin.schedule.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentScheduleCreateBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.ChooseDialog
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.toast
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
        eventSuccessCreateSchedule.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    toast(requireContext(), it.data)
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
                    checkButtonCreateActivate()
                }
            }
        }
        switchHalfBreak.setOnCheckedChangeListener { _, isChecked ->
            textInputHalfBreakTime.isVisible = isChecked
        }
        switchBetweenBreak.setOnCheckedChangeListener { _, isChecked ->
            textInputBreakBetweenTime.isVisible = isChecked
        }
        editGameCount.addTextChangedListener {
            checkButtonCreateActivate()
        }
        editHalfTime.addTextChangedListener {
            checkButtonCreateActivate()
        }
        buttonCreate.setOnClickListener {
            viewModel.createSchedule(
                countGame = binding.editGameCount.text.toString(),
                halfTime = binding.editHalfTime.text.toString(),
                timeHalfBreak = binding.editHalfBreakTime.text.toString(),
                timeAfterBreak = binding.editBreakBetweenTime.text.toString()
            )
        }
    }

    private fun initViews() = binding.run {
        layoutSelectData.textTitle.text = getString(R.string.schedule_create__date)
        layoutSelectTime.textTitle.text = getString(R.string.schedule_create__game_start)
        layoutSelectStadium.textTitle.text = getString(R.string.schedule_create__stadium)
        checkButtonCreateActivate()
    }

    private fun showDateDialog() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val datePicker = builder.build()
        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            Timber.d("Время Unix =Дата $it Дата Unix ${it/1000}")
            val dateFormat = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
            val date = dateFormat.format(Date(it))
            binding.layoutSelectData.textSubtitle.text = date
            viewModel.selectDate(it / 1000)
            checkButtonCreateActivate()
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
            viewModel.selectTime(timePicker.hour, timePicker.minute)
            checkButtonCreateActivate()
        }

    }

    private fun checkButtonCreateActivate(){
        binding.apply {
            val date = layoutSelectData.textSubtitle.text.toString()
            val time = layoutSelectTime.textSubtitle.text.toString()
            val stadium = layoutSelectStadium.textSubtitle.text.toString()
            val gameCount = editGameCount.text.toString()
            val halfTime = editHalfTime.text.toString()
            buttonCreate.isEnabled = !listOf(date, time,  stadium, gameCount, halfTime).any(String::isBlank)
        }
    }

}