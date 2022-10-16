package com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.databinding.DialogChooseBinding
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment

class ChooseDialog(): BaseBottomSheetDialogFragment<DialogChooseBinding>() {

    companion object {
        const val BUNDLE_DATA = "values for recycler"
        const val BUNDLE_TITLE = "tittle choose dialog"
        const val BUNDLE_HEIGHT = "BUNDLE_MAX_HEIGHT"
        const val ARGUMENT_KEY_REQUEST = "ARGUMENT_KEY_REQUEST"

        fun prepareBundle(title: String, data: List<ChooseModel>, height: Double = 1.0): Bundle{
            return bundleOf(
                BUNDLE_TITLE to title,
                BUNDLE_DATA to data,
                BUNDLE_HEIGHT to height
            )
        }

        fun newInstance(bundle: Bundle): ChooseDialog{
            return ChooseDialog().apply {
                arguments = bundle
            }
        }
    }

    private lateinit var chooseAdapter: ChooseAdapter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChooseBinding {
        return DialogChooseBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val height = arguments?.getDouble(BUNDLE_HEIGHT, 0.5) ?: 0.5
        setMaxHeight(height)
        binding.textTitle.text = arguments?.getString(BUNDLE_TITLE)
        initRecycler()
        setupListener()
    }

    private fun setupListener() = binding.run {
        imageClose.setOnClickListener {
            dismiss()
        }
        buttonChoose.setOnClickListener {
            setFragmentResult(ARGUMENT_KEY_REQUEST, bundleOf(BUNDLE_DATA to chooseAdapter.getData()[chooseAdapter.choosePosition]))
            dismiss()
        }
    }

    private fun initRecycler() = binding.run {
        arguments?.getParcelableArrayList<ChooseModel>(BUNDLE_DATA)?.let { data ->
            val choosePosition = data.indexOfFirst { it.isChoose }
            buttonChoose.isEnabled = choosePosition != -1
            chooseAdapter = ChooseAdapter(choosePosition).apply {
                setData(data)
                onClickItem = {
                    buttonChoose.isEnabled = it
                }
            }
            recyclerChoose.adapter = chooseAdapter

        }
    }

}