package com.glushko.sportcommunity.presentation.team.squad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation

import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.databinding.ItemSquadBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class SquadAdapters: BaseAdapter<SquadPlayerUI, ItemSquadBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSquadBinding = ItemSquadBinding.inflate(inflater)

    override fun bindViewHolder(holder: ViewBindingHolder, data: SquadPlayerUI) {
        holder.binding.apply {
            imagePlayerAvatar.load(data.avatarUrl){
                transformations(CircleCropTransformation())
            }
            textPlayerName.text = data.playerName
            textAmplua.text = data.amplua
        }
    }
}