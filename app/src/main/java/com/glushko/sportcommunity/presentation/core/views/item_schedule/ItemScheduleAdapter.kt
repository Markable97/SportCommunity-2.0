package com.glushko.sportcommunity.presentation.core.views.item_schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.admin.schedule.model.TimeScheduleUI
import com.glushko.sportcommunity.databinding.ItemScheduleTimeBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class ItemScheduleAdapter: BaseAdapter<TimeScheduleUI, ItemScheduleTimeBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemScheduleTimeBinding  = ItemScheduleTimeBinding.inflate(inflater, parent, false)

    override fun bindViewHolder(holder: ViewBindingHolder, data: TimeScheduleUI) = holder.binding.run {
        textTime.text = data.time
        cardTime.setBackgroundResource (if (data.match != null) R.color.schedule_bg_item_with_match  else R.color.schedule_bg_item_stadium)
    }
}