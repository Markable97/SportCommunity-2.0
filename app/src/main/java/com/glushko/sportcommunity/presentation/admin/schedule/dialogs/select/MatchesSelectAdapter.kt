package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.select

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.databinding.ItemAssignMatchBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter
import com.glushko.sportcommunity.util.Constants

class MatchesSelectAdapter(
    var onClickItem: ((MatchUI?) -> Unit)
): BaseAdapter<MatchUI, ItemAssignMatchBinding>() {

    private var selectPosition = -1

    init {
        listenerWithPosition = { item, position ->
            notifyItemChanged(selectPosition)
            selectPosition = if (position != selectPosition){
                onClickItem.invoke(item)
                position

            } else {
                onClickItem.invoke(null)
                -1
            }
            notifyItemChanged(selectPosition)

        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemAssignMatchBinding  = ItemAssignMatchBinding.inflate(inflater, parent, false)

    override fun bindViewHolder(holder: ViewBindingHolder, data: MatchUI) {
        with(holder.binding){
            textDivision.text = data.tournamentName
            textTour.text = data.tour
            textTeamHome.text = data.teamHomeName
            imageTeamHome.load("${Constants.BASE_URL_IMAGE}${data.teamHomeName}.png")
            textTeamGuest.text = data.teamGuestName
            imageTeamGuest.load("${Constants.BASE_URL_IMAGE}${data.teamGuestName}.png")
            root.setCardBackgroundColor(root.context.getColor(if (holder.adapterPosition == selectPosition) R.color.purple_200 else R.color.white))
        }
    }
}