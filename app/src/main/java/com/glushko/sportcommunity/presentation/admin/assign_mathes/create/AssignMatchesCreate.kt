package com.glushko.sportcommunity.presentation.admin.assign_mathes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesCreateBinding
import com.glushko.sportcommunity.presentation.admin.assign_mathes.AssignMatchesViewModel
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.ChooseDialog
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Result

class AssignMatchesCreate :
    BaseXmlFragment<FragmentAssignMatchesCreateBinding>(R.layout.fragment_assign_matches_create) {

    private val viewModel: AssignMatchesViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignMatchesCreateBinding = FragmentAssignMatchesCreateBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() = viewModel.run {
        liveDataDivisions.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {}
            }
        }
    }

    private fun setupListeners() = binding.run {
        layoutChooserDivision.root.setOnClickListener {
            chooseOption(layoutChooserDivision.textTitle.text.toString(), viewModel.getDivisions())
        }
        layoutChooserTour.root.setOnClickListener {

        }
    }

    private fun chooseOption(title: String, dataset: List<ChooseModel>) {
        openChooseDialog(
            ChooseDialog.prepareBundle(
                title = title,
                data = dataset
            )
        ) { bundle ->
            (bundle.getParcelable(ChooseDialog.BUNDLE_DATA) as? ChooseModel)?.let { option ->
                val position = option.position ?: 0
                when (option.valueType) {
                    Constants.TYPE_VALUE_DIVISION -> {
                        binding.layoutChooserDivision.textSubtitle.text = option.valueDisplay
                    }
                    Constants.TYPE_VALUE_TOUR -> {}
                }
            }
        }
    }

    private fun initViews() = binding.run {
        layoutChooserDivision.textTitle.text = getString(R.string.assign_matches__division)
        layoutChooserTour.textTitle.text = getString(R.string.assign_matches__tour)
    }

}