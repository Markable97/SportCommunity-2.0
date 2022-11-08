package com.glushko.sportcommunity.presentation.admin.edit_match.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.DialogTimeSelectBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.base.BaseBottomSheetDialogFragment
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeSelectDialog : BaseBottomSheetDialogFragment<DialogTimeSelectBinding>() {

    private val args: TimeSelectDialogArgs by navArgs()
    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogTimeSelectBinding = DialogTimeSelectBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        initViews()
    }

    private fun initViews() = binding.run {
        val timeParts = args.time.split(":")
        if (timeParts.size == 2){
            editMinutes.setText(timeParts[0])
            editSeconds.setText(timeParts[1])
        }
    }

    private fun setupListener() = binding.run{
        imageClose.setSafeOnClickListener {
            findNavController().popBackStack()
        }
        editMinutes.doAfterTextChanged {
            checkButtonEnable()
        }
        editSeconds.doAfterTextChanged {
            checkButtonEnable()
        }
        buttonSelect.setSafeOnClickListener {
            viewModel.setTimeToPLayer(editMinutes.text.toString(), editSeconds.text.toString(), args.position)
            findNavController().popBackStack()
        }
    }

    private fun checkButtonEnable() = binding.run{
        buttonSelect.isEnabled = editMinutes.text.toString().isNotBlank() && editSeconds.text.toString().isNotBlank()
    }
}