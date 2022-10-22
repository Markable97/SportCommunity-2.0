package com.glushko.sportcommunity.presentation.admin.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentScheduleBinding
import com.glushko.sportcommunity.presentation.admin.schedule.adapters.CalendarAdapter
import com.glushko.sportcommunity.presentation.admin.schedule.adapters.ScheduleAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ScheduleFragment: BaseXmlFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {

    private val viewModel: ScheduleViewModel by viewModels()

    private val adapterSchedule by lazy {
        ScheduleAdapter(
            onclickTime = {stadium, timeSchedule ->

            }
        )
    }

    private val adapterCalendar by lazy {
        CalendarAdapter(
            onItemClick = {
                Timber.d("Новый день = $it")
                viewModel.getSchedule(it.unixDate)
            }
        )
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleBinding = FragmentScheduleBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        setupObservers()
    }

    private fun setupObservers() = viewModel.run {
        liveDataCalendar.observe(viewLifecycleOwner){
            adapterCalendar.setData(it)
        }
        liveDataSchedule.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    adapterSchedule.setData(it.data)
                }
            }
        }
    }

    private fun initRecycler() = binding.run {
        recyclerSchedule.adapter = adapterSchedule
        recyclerCalendar.adapter = adapterCalendar
    }
}