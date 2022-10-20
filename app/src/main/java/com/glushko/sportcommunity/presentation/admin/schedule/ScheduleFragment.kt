package com.glushko.sportcommunity.presentation.admin.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesBinding
import com.glushko.sportcommunity.databinding.FragmentScheduleBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment

class ScheduleFragment: BaseXmlFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleBinding = FragmentScheduleBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}