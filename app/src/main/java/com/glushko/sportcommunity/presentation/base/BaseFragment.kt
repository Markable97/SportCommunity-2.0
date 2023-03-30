package com.glushko.sportcommunity.presentation.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.glushko.sportcommunity.presentation.main_screen.ui.MainViewModel


open class BaseFragment: Fragment() {
    protected val mainViewModel: MainViewModel by activityViewModels()

}