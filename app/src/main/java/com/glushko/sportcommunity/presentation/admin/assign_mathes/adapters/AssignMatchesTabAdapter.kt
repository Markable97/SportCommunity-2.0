package com.glushko.sportcommunity.presentation.admin.assign_mathes.adapters

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.admin.assign_mathes.create.AssignMatchesCreate
import com.glushko.sportcommunity.presentation.admin.assign_mathes.list.AssignMatchesList

class AssignMatchesTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = TABS_COUNT

    override fun createFragment(position: Int) = mapPosition(position)

    private fun mapPosition(position: Int) = when (position) {
        INDEX_TAB_CREATION -> AssignMatchesCreate()
        INDEX_TAB_LIST -> AssignMatchesList()
        else -> error("Unexpected pager position")
    }

    companion object {
        private const val TABS_COUNT = 2
        const val INDEX_TAB_CREATION = 1
        const val INDEX_TAB_LIST = 0

        @StringRes
        fun getPageTitle(position: Int) = when (position) {
            INDEX_TAB_CREATION -> R.string.assign_matches__tab_create
            INDEX_TAB_LIST -> R.string.assign_matches__tab_matches
            else -> error("Unexpected position")
        }
    }
}
