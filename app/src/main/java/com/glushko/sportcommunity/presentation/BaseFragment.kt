package com.glushko.sportcommunity.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.glushko.sportcommunity.presentation.main_screen.vm.MainViewModel


open class BaseFragment: Fragment() {
    protected lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

}