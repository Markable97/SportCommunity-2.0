package com.glushko.sportcommunity.presentation.admin.edit_match.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentMatchesEditBinding
import com.glushko.sportcommunity.presentation.admin.edit_match.EditMatchViewModel
import com.glushko.sportcommunity.presentation.admin.edit_match.list.adapters.AssignedMatchesEditAdapters
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EditMatchListFragment: BaseXmlFragment<FragmentMatchesEditBinding>(R.layout.fragment_matches_edit) {

    val args: EditMatchListFragmentArgs by navArgs()

    private val viewModel: EditMatchViewModel by hiltNavGraphViewModels(R.id.nested_navigation_edit_matches)

    private val adapterMatches: AssignedMatchesEditAdapters by lazy{
        AssignedMatchesEditAdapters().apply {
            listenerWithPosition = { item, _ ->
                viewModel.setMatch(item)
                findNavController().navigate(
                    EditMatchListFragmentDirections.actionEditMatchListFragmentToProtocolFragment()
                )
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMatchesEditBinding = FragmentMatchesEditBinding.inflate(inflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("mathcId = ${args.match?.matchId}")
        initRecycler()
        setupObservers()
    }

    private fun setupObservers() = viewModel.run {
        liveDataAssignMatches.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    Timber.d("LiveData matches ${it.data}")
                    if (match != null) {
                        it.data.firstOrNull()?.let { _ ->
                            viewModel.setMatch(match.copy(isSaved = true), true)
                            val navOptions = NavOptions.Builder().setPopUpTo(R.id.editMatchListFragment, true).build()
                            val action = EditMatchListFragmentDirections.actionEditMatchListFragmentToProtocolFragment()
                            findNavController().navigate(
                                action, navOptions
                            )
                        }
                    } else {
                        adapterMatches.setData(it.data)
                    }
                }
            }
        }
    }

    private fun initRecycler() = binding.run {
        recyclerMatches.adapter = adapterMatches
    }
}