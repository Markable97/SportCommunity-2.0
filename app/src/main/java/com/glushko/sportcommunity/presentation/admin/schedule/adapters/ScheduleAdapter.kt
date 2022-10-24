package com.glushko.sportcommunity.presentation.admin.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.ScheduleUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.TimeScheduleUI
import com.glushko.sportcommunity.databinding.ItemScheduleBinding
import com.glushko.sportcommunity.presentation.base.BaseAdapter

class ScheduleAdapter(
    private val onclickTime: ( (StadiumUI, TimeScheduleUI) -> Unit )
): BaseAdapter<ScheduleUI, ItemScheduleBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemScheduleBinding = ItemScheduleBinding.inflate(inflater, parent, false)

    override fun bindViewHolder(holder: ViewBindingHolder, data: ScheduleUI) = holder.binding.root.run{
        nameStadium = data.stadium.name
        isOpen = data.isOpen
        setTimes(data.times)
        setOnOpenClickListener {
            data.isOpen = !data.isOpen
            notifyItemChanged(holder.adapterPosition)
        }
        setOnTimeClickListener {
            onclickTime.invoke(data.stadium, it)
        }
    }
}