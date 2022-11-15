package com.glushko.sportcommunity.presentation.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.databinding.ItemChooseBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class MultiChooseAdapter(
    val onClickItem: (ChooseModel,Int) -> Unit
): BaseAdapter<ChooseModel, ItemChooseBinding>() {

    init {
        listenerWithPosition = {item, position ->
            item.isChoose = !item.isChoose
            notifyItemChanged(position)
            onClickItem.invoke(item, position)
        }
    }

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