package com.glushko.sportcommunity.presentation.team.squad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import com.glushko.sportcommunity.data.squad.model.SquadPlayer
import com.glushko.sportcommunity.databinding.ItemSquadBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class SquadAdapters: BaseAdapter<SquadPlayer, ItemSquadBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSquadBinding = ItemSquadBinding.inflate(inflater)

    override fun bindViewHolder(holder: ViewBindingHolder, data: SquadPlayer) {
        holder.binding.apply {
            //TODO добавить загрузку фотки игрока
            textPlayerName.text = data.playerName
            textAmplua.text = data.amplua
        }
    }
}