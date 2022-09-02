package com.glushko.sportcommunity.presentation.team.squad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentSquadBinding
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.presentation.team.squad.adapters.SquadAdapters
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SquadFragment: BaseXmlFragment<FragmentSquadBinding>(R.layout.fragment_squad){

    private val viewModel: SquadViewModel by viewModels()

    private val adapterSquad by lazy {
        SquadAdapters()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSquadBinding = FragmentSquadBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSquad.adapter = adapterSquad
        setObservers()
    }

    private fun setObservers() = viewModel.run {
        liveDataSquadList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapterSquad.setData(it.data!!)
                }
            }
        }
    }

}