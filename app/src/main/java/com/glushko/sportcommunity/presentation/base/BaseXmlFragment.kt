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
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.ChooseDialog
import java.lang.IllegalArgumentException

abstract class BaseXmlFragment<B : ViewBinding>(@LayoutRes layout: Int) : Fragment(layout) {

    private var _viewBinding: B? = null
    protected val binding get() = checkNotNull(_viewBinding)

    private var actionChooseDialog: ((ChooseModel?) -> Unit)? = null

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

    /**
     * Принимает сигнал от ChooseDialog и выполяется действие, которое мы передали
     * Обязательно нужен Bundle, чтобы определить, что вернулось
     **/
    fun openChooseDialog(bundle: Bundle, isNestedFragment: Boolean = false, action: ((ChooseModel?) -> Unit)) {
        actionChooseDialog = action
        val fragmentManger =
            if (!isNestedFragment) parentFragmentManager else requireParentFragment().parentFragmentManager
        ChooseDialog.newInstance(bundle).show(fragmentManger, "show choose dialog")
        fragmentManger.setFragmentResultListener(
            ChooseDialog.ARGUMENT_KEY_REQUEST,
            viewLifecycleOwner
        ) { _, bundleFromDialog ->
            actionChooseDialog?.invoke(bundleFromDialog.getParcelable(ChooseDialog.BUNDLE_DATA) as? ChooseModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actionChooseDialog = null
        _viewBinding = null
    }
}
