package com.glushko.sportcommunity.presentation.admin.assign_mathes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentAssignMatchesListBinding
import com.glushko.sportcommunity.presentation.admin.assign_mathes.AssignMatchesViewModel
import com.glushko.sportcommunity.presentation.admin.assign_mathes.adapters.AssignMatchesListAdapter
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.util.Result

class AssignMatchesList :
    BaseXmlFragment<FragmentAssignMatchesListBinding>(R.layout.fragment_assign_matches_list) {

    private val viewModel: AssignMatchesViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val matchesAdapter by lazy{
        AssignMatchesListAdapter().apply {
            onClickItem = {
                viewModel.checkButtonDelete()
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignMatchesListBinding = FragmentAssignMatchesListBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerMatches.adapter = matchesAdapter
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() = binding.run {
        buttonDelete.setOnClickListener {
            viewModel.deleteMatches()
        }
    }

    private fun setupObservers() = viewModel.run {
        liveDataAssignMatches.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    matchesAdapter.setData(it.data)
                }
            }
        }
        liveDataCheckButtonDelete.observe(viewLifecycleOwner){
            binding.buttonDelete.isEnabled = it
        }
    }

}