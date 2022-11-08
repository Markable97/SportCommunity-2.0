package com.glushko.sportcommunity.presentation.admin.edit_match.edit.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.glushko.sportcommunity.presentation.admin.edit_match.edit.player_select.TeamFirstFragment
import com.glushko.sportcommunity.presentation.admin.edit_match.edit.player_select.TeamSecondFragment

class PlayersOfTeamsTabAdapters(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TABS_COUNT

    override fun createFragment(position: Int) = when(position) {
        TAB_TEAM_FIRST -> TeamFirstFragment()
        else -> TeamSecondFragment()
    }

    companion object {
        private const val TABS_COUNT = 2
        const val TAB_TEAM_FIRST = 0
        private const val TAB_TEAM_SECOND = 1
    }
}