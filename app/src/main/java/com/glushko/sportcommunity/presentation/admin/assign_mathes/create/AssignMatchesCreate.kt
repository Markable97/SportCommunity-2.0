package com.glushko.sportcommunity.presentation.admin.assign_mathes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.model.ChooseModel
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesCreateBinding
import com.glushko.sportcommunity.presentation.admin.assign_mathes.AssignMatchesViewModel
import com.glushko.sportcommunity.presentation.admin.assign_mathes.adapters.AssignMatchesListAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.ChooseDialog
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Result

class AssignMatchesCreate :
    BaseXmlFragment<FragmentAssignMatchesCreateBinding>(R.layout.fragment_assign_matches_create) {

    private val viewModel: AssignMatchesViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val matchesAdapter by lazy{
        AssignMatchesListAdapter().apply {
            onClickItem = {
                viewModel.checkButtonAssign()
            }
        }
    }

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
        liveDataUnassignedMatches.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    matchesAdapter.setData(it.data)
                }
            }
        }
        liveDataCheckButtonAssign.observe(viewLifecycleOwner){
            binding.buttonAssign.isEnabled = it
        }
    }

    private fun setupListeners() = binding.run {
        layoutChooserDivision.root.setOnClickListener {
            chooseOption(layoutChooserDivision.textTitle.text.toString(), viewModel.getDivisions())
        }
        layoutChooserTour.root.setOnClickListener {
            chooseOption(layoutChooserTour.textTitle.text.toString(), viewModel.getTours())
        }
        buttonAssign.setOnClickListener {
            viewModel.assignMatches()
        }
    }

    private fun chooseOption(title: String, dataset: List<ChooseModel>) {
        openChooseDialog(
            ChooseDialog.prepareBundle(
                title = title,
                data = dataset
            )
        ) { data ->
            data?.let { option ->
                when (option.valueType) {
                    Constants.TYPE_VALUE_DIVISION -> {
                        binding.layoutChooserDivision.textSubtitle.text = option.valueDisplay
                        binding.layoutChooserTour.textSubtitle.text = ""
                        viewModel.getUnassignedTours(option)
                    }
                    Constants.TYPE_VALUE_TOUR -> {
                        binding.layoutChooserTour.textSubtitle.text = option.valueDisplay
                        viewModel.getUnassignedMatches(option)
                    }
                }
            }
        }
    }

    private fun initViews() = binding.run {
        recyclerMatches.adapter = matchesAdapter
        layoutChooserDivision.textTitle.text = getString(R.string.assign_matches__division)
        layoutChooserTour.textTitle.text = getString(R.string.assign_matches__tour)
        recyclerMatches.adapter = matchesAdapter
    }

}