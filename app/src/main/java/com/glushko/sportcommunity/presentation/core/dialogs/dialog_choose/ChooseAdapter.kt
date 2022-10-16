package com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.databinding.ItemChooseBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class ChooseAdapter(var choosePosition: Int): BaseAdapter<ChooseModel, ItemChooseBinding>() {

    init {
        listenerWithPosition = {item, position ->
            item.isChoose = !item.isChoose
            notifyItemChanged(choosePosition)
            choosePosition = if (choosePosition != position) {
                position
            } else {
                -1
            }
            onClickItem?.invoke(choosePosition != -1)
            notifyItemChanged(choosePosition)
        }
    }

    var onClickItem: ((Boolean) -> Unit)? = null

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemChooseBinding = ItemChooseBinding.inflate(inflater, parent, false)

    override fun bindViewHolder(holder: ViewBindingHolder, data: ChooseModel) {
        with(holder.binding){
            textItem.text = data.valueDisplay
            radioButtonItem.isChecked = data.isChoose
        }
    }
}