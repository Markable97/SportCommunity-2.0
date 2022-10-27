package com.glushko.sportcommunity.presentation.admin.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentScheduleBinding
import com.glushko.sportcommunity.presentation.admin.schedule.adapters.CalendarAdapter
import com.glushko.sportcommunity.presentation.admin.schedule.adapters.ScheduleAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ScheduleFragment : BaseXmlFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {

    companion object {
        const val REQUEST_CODE = "REQUEST_CODE"
        const val BUNDLE_POSITION = "BUNDLE_POSITION"
        const val BUNDLE_DELETE = "BUNDLE_DELETE"
        const val BUNDLE_MATCH = "BUNDLE_MATCH"
    }

    private val viewModel: ScheduleViewModel by viewModels()

    private val adapterSchedule by lazy {
        ScheduleAdapter(
            onclickTime = { stadium, timeSchedule, position ->
                Timber.d("Новое время = $stadium $timeSchedule")
                if (timeSchedule.match != null) {
                    findNavController().navigate(
                        ScheduleFragmentDirections.actionScheduleFragmentToMatchViewBottomSheetDialog(
                            match = timeSchedule,
                            stadium = stadium,
                            position = position
                        )
                    )
                } else {
                    findNavController().navigate(
                        ScheduleFragmentDirections.actionScheduleFragmentToMatchesSelectBottomSheetDialog(
                            stadium,
                            timeSchedule,
                            viewModel.liveDataAssignMatches.value?.data?.toTypedArray()
                                ?: emptyArray(),
                            position
                        )
                    )
                }
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

    private fun setupObservers() {
        with(viewModel) {
            liveDataCalendar.observe(viewLifecycleOwner) {
                adapterCalendar.setData(it)
            }
            liveDataSchedule.observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        adapterSchedule.setData(it.data)
                    }
                }
            }
            eventAddMatchInSchedule.observe(viewLifecycleOwner){
                adapterSchedule.notifyItemChanged(it)
            }
        }
        setFragmentResultListener(REQUEST_CODE) { _, bundle ->
            adapterSchedule.notifyItemChanged(bundle.getInt(BUNDLE_POSITION, -1))
            viewModel.actionWithAssignedMatches(
                isDelete = bundle.getBoolean(BUNDLE_DELETE),
                math = bundle.getParcelable(BUNDLE_MATCH)
            )
        }
    }

    private fun initRecycler() = binding.run {
        recyclerSchedule.adapter = adapterSchedule
        recyclerCalendar.adapter = adapterCalendar
    }
}