package com.glushko.sportcommunity.presentation.admin.edit_match.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.databinding.ItemAssignMatchEditBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter
import com.glushko.sportcommunity.util.Constants

class AssignedMatchesEditAdapters : BaseAdapter<MatchUI, ItemAssignMatchEditBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemAssignMatchEditBinding = ItemAssignMatchEditBinding.inflate(inflater, parent, false)

    override fun bindViewHolder(holder: ViewBindingHolder, data: MatchUI) {
        with(holder.binding) {
            textDivision.text = data.tournamentName
            textTour.text = data.tour
            textTeamHome.text = data.teamHomeName
            imageTeamHome.load("${Constants.BASE_URL_IMAGE}${data.teamHomeName}.png")
            textTeamGuest.text = data.teamGuestName
            imageTeamGuest.load("${Constants.BASE_URL_IMAGE}${data.teamGuestName}.png")
            editGoalsHome.isEnabled = false
            editGoalsHome.setText(
                if (data.teamHomeGoals != null) {
                    data.teamHomeGoals.toString()
                } else {
                    ""
                }
            )
            editGoalsGuest.isEnabled = false
            editGoalsGuest.setText(
                if (data.teamGuestGoals != null) {
                    data.teamGuestGoals.toString()
                } else {
                    ""
                }
            )
        }
    }
}