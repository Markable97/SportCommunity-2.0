package com.glushko.sportcommunity.presentation.admin.assign_mathes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.databinding.ItemAssignMatchBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter
import com.glushko.sportcommunity.util.Constants

class AssignMatchesListAdapter : BaseAdapter<MatchUI, ItemAssignMatchBinding>() {

    init {
        listenerWithPosition = {item, position ->
            items[position].isSelect = !items[position].isSelect
            notifyItemChanged(position)
            onClickItem?.invoke()
        }
    }

    var onClickItem: (() -> Unit)? = null

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
            root.setCardBackgroundColor(root.context.getColor(if (data.isSelect) R.color.purple_200 else R.color.white))
        }
    }
}