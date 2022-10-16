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
            choosePosition = if (choosePosition != position) {
                if (choosePosition != -1){
                    items[choosePosition].isChoose = false
                    notifyItemChanged(choosePosition)
                }
                position
            } else {
                notifyItemChanged(choosePosition)
                -1
            }
            notifyItemChanged(choosePosition)
            onClickItem?.invoke(choosePosition != -1)
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