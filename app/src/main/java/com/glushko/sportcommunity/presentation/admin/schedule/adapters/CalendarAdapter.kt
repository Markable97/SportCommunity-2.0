package com.glushko.sportcommunity.presentation.admin.schedule.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.CalendarDayUI
import com.glushko.sportcommunity.databinding.ItemCalendarDayBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class CalendarAdapter(
    private val onItemClick: (CalendarDayUI) -> Unit
): BaseAdapter<CalendarDayUI, ItemCalendarDayBinding>() {

    private var selectPosition = -1

    init {
        listenerWithPosition = { item, position ->
            if (position != selectPosition){
                onItemClick.invoke(item)
            }
            notifyItemChanged(selectPosition)
            selectPosition = position
            notifyItemChanged(selectPosition)
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemCalendarDayBinding = ItemCalendarDayBinding.inflate(inflater, parent, false)

    override fun bindViewHolder(holder: ViewBindingHolder, data: CalendarDayUI) = holder.binding.run {
        textDayOfWeek.text = data.dayOfWeek
        textDayOfWeek.typeface = if (holder.adapterPosition == selectPosition) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        textDayOfMonth.text = data.dayOfMonth
        textDayOfMonth.background = root.context.getDrawable(if (holder.adapterPosition == selectPosition) R.drawable.bg_rounded_day_of_mounth_select else R.drawable.bg_rounded_day_of_mounth )

    }
}