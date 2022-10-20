package com.glushko.sportcommunity.presentation.admin.schedule.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.CalendarDayUI
import com.glushko.sportcommunity.databinding.ItemCalendarDayBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter
import com.glushko.sportcommunity.util.extensions.setTextColorRes

class CalendarAdapter(
    private val onItemClick: (CalendarDayUI) -> Unit
): BaseAdapter<CalendarDayUI, ItemCalendarDayBinding>() {

    private var selectPosition = 0

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
        textDayOfMonth.text = data.dayOfMonth.toString()
        if (holder.adapterPosition == selectPosition) {
            textDayOfWeek.typeface = Typeface.DEFAULT_BOLD
            textDayOfMonth.background = root.context.getDrawable(R.drawable.bg_rounded_day_of_mounth_select)
            textDayOfMonth.setTextColorRes(R.color.white)
        } else {
            textDayOfWeek.typeface = Typeface.DEFAULT
            textDayOfMonth.background = root.context.getDrawable(R.drawable.bg_rounded_day_of_mounth)
            textDayOfMonth.setTextColorRes(R.color.black)
        }
    }
}