package com.glushko.sportcommunity.presentation.calendar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.BaseFragment
import timber.log.Timber

class CalendarFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.liveDataSelectedDivision.observe(viewLifecycleOwner){
            Timber.d("Пришел новый дивизион = $it")
        }
    }

}