package com.glushko.sportcommunity.presentation.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.presentation.main_screen.vm.MainViewModel


open class BaseFragment: Fragment() {
    protected val mainViewModel: MainViewModel by activityViewModels()

}