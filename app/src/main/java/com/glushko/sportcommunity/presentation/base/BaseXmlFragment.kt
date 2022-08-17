package com.glushko.sportcommunity.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseXmlFragment<B : ViewBinding>(@LayoutRes layout: Int) : Fragment(layout) {

    private var _viewBinding: B? = null
    protected val binding get() = checkNotNull(_viewBinding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = initBinding(inflater, container)
        return binding.root
    }

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): B

    fun navigate(@IdRes resId: Int, args: Bundle? = null, navOption: NavOptions? = null) {
        findNavController().navigate(resId = resId, args = args, navOptions = navOption)
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
