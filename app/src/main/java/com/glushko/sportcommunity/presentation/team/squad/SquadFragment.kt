package com.glushko.sportcommunity.presentation.team.squad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentSquadBinding
import com.glushko.sportcommunity.presentation.base.BaseXmlFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquadFragment: BaseXmlFragment<FragmentSquadBinding>(R.layout.fragment_squad){

    private val viewModel: SquadViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSquadBinding = FragmentSquadBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            SquadScreen(viewModel = viewModel, navController = findNavController())
        }
    }

    /*private fun initSearchView() = binding.run {
        searchView.isIconified = false
        //The above line will expand it to fit the area as well as throw up the keyboard
        //To remove the keyboard, but make sure you keep the expanded version:
        searchView.clearFocus()
        searchView.setOnCloseListener {
            searchView.clearFocus()
            true
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchPlayer(newText)
                return false
            }

        })
    }*/

}