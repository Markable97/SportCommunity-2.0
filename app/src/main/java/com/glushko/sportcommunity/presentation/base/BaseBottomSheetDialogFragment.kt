package com.glushko.sportcommunity.presentation.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.viewbinding.ViewBinding
import com.glushko.sportcommunity.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialogFragment<B : ViewBinding> : BottomSheetDialogFragment() {
    private var _viewBinding: B? = null
    protected val binding get() = checkNotNull(_viewBinding)

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = initBinding(inflater, container)
        return binding.root
    }

    private fun getScreenHeight( activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    fun setMaxHeight(height: Double) {
        val currentHeight = (getScreenHeight(requireActivity()) * height).toInt()
        dialog?.setOnShowListener {
            val bottomSheetDialog = dialog as? BottomSheetDialog
            val bottomSheetInternal: View? =
                bottomSheetDialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetInternal?.apply {
                val behavior = BottomSheetBehavior.from(this)
                if (currentHeight != 0) {
                    behavior.peekHeight = currentHeight
                    this.layoutParams.height = currentHeight
                }
                behavior.skipCollapsed = false
            }
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?):
        Dialog = BottomSheetDialog(requireContext(), theme)

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
