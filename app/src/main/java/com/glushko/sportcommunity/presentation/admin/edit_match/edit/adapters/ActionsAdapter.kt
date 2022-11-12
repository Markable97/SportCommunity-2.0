package com.glushko.sportcommunity.presentation.admin.edit_match.edit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.databinding.ItemActionEditBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import timber.log.Timber

class ActionsAdapter(
    private val onClickButtonDelete: (Int) -> Unit,
    private val onClickButtonSave: (Int) -> Unit,
    private val onClickButtonEdit: (Int) -> Unit,
    private val onClickAction: (Int) -> Unit,
    private val onClickTime: (Int, String) -> Unit,
    private val onClickPlayer: (Int) -> Unit,
    private val onClickPlayerAssistant: (Int) -> Unit,
) : BaseAdapter<PlayerWithActionUI, ItemActionEditBinding>() {



    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemActionEditBinding {
        return ItemActionEditBinding.inflate(inflater, parent, false)
    }

    override fun bindViewHolder(holder: ViewBindingHolder, data: PlayerWithActionUI) {
        with(holder.binding){
            inputLayoutAssistant.isVisible = data.action?.actionId == Constants.TYPE_ACTION_GOAL

            editPlayer.setText(data.player?.playerName ?: "")
            editTime.setText(data.time ?: "")
            editAction.setText(data.action?.actionName ?: "")
            editAssistant.setText(data.playerAssist?.playerName ?: "")

            buttonSave.isVisible = !data.isSaving
            buttonDelete.isVisible = !data.isSaving
            buttonEdit.isVisible = data.isSaving

            buttonDelete.setSafeOnClickListener {
                onClickButtonDelete.invoke(holder.adapterPosition)
            }
            buttonSave.setSafeOnClickListener {
                onClickButtonSave.invoke(holder.adapterPosition)
            }
            buttonEdit.setSafeOnClickListener {
                onClickButtonEdit.invoke(holder.adapterPosition)
            }
            editAction.setSafeOnClickListener {
                onClickAction.invoke(holder.adapterPosition)
            }
            editTime.setSafeOnClickListener {
                onClickTime.invoke(holder.adapterPosition, editTime.text.toString())
            }
            editPlayer.setSafeOnClickListener {
                onClickPlayer.invoke(holder.adapterPosition)
            }
            editAssistant.setSafeOnClickListener {
                onClickPlayerAssistant.invoke(holder.adapterPosition)
            }
        }
    }
}