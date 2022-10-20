package com.glushko.sportcommunity.presentation.core.views.item_schedule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.TimeScheduleUI
import com.glushko.sportcommunity.databinding.ViewScheduleItemBinding
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener

class ItemSchedule @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewScheduleItemBinding =
        ViewScheduleItemBinding.inflate(LayoutInflater.from(context), this, true)

    var isOpen: Boolean
        get() = binding.recyclerTime.isVisible
        set(value) {
            logicOpen(value)
        }

    var nameStadium: String
        get() = binding.textStadiumName.text.toString()
        set(value) {
            binding.textStadiumName.text = value
        }

    private var onOpenClickListener: (() -> Unit)? = null
    private var onTimeClickListener: ((TimeScheduleUI) -> Unit)? = null

    private val adapterTime by lazy {
        ItemScheduleAdapter().apply {
            listener = {
                onTimeClickListener?.invoke(it)
            }
        }
    }

    init {
        setListeners()
        binding.recyclerTime.adapter = adapterTime
    }

    private fun setListeners() = binding.run {
        textStadiumName.setSafeOnClickListener {
            onOpenClickListener?.invoke()
        }
        imageOpen.setSafeOnClickListener {
            onOpenClickListener?.invoke()
        }
    }

    private fun logicOpen(isOpen: Boolean) = binding.run {
        imageOpen.rotation = 180F
        recyclerTime.isVisible = isOpen
        root.setBackgroundColor(if (isOpen) R.color.bg_tournament_table_team_selected else R.color.white)
    }

    private fun setTimes(list: List<TimeScheduleUI>){
        adapterTime.setData(list)
    }

    fun setOnOpenClickListener(callback: ()->Unit){
        onOpenClickListener = callback
    }

    fun setOnTimeClickListener(callback: (TimeScheduleUI) -> Unit){
        onTimeClickListener = callback
    }
}
