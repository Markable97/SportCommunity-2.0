package com.glushko.sportcommunity.presentation.admin.edit_match.edit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.databinding.ItemActionEditBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter
import com.glushko.sportcommunity.util.Constants

class ActionsAdapter : BaseAdapter<PlayerWithActionUI, ItemActionEditBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemActionEditBinding {
        return ItemActionEditBinding.inflate(inflater, parent, false)
    }

    override fun bindViewHolder(holder: ViewBindingHolder, data: PlayerWithActionUI) {
        with(holder.binding){
            val isEnable = data.isSaving

            inputLayoutAssistant.isVisible = data.action?.actionId == Constants.TYPE_ACTION_GOAL

            editPlayer.setText(data.player?.playerName ?: "")
            editTime.setText(data.time ?: "")
            editAction.setText(data.action?.actionName ?: "")
            editAssistant.setText(data.playerAssist?.playerName ?: "")

            buttonSave.isVisible = !data.isSaving
            buttonDelete.isVisible = !data.isSaving
            buttonEdit.isVisible = data.isSaving
        }
    }
}