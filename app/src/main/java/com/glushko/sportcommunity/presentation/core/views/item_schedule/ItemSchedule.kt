package com.glushko.sportcommunity.presentation.core.views.item_schedule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.admin.schedule.model.TimeScheduleUI
import com.glushko.sportcommunity.databinding.ViewScheduleItemBinding
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener
import com.glushko.sportcommunity.util.extensions.setTextColorRes
import com.google.android.flexbox.*


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
        binding.recyclerTime.layoutManager = FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
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
        recyclerTime.isVisible = isOpen
        imageOpen.setImageDrawable(
            context.getDrawable(
                if (isOpen) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
            )
        )
        textStadiumName.setTextColorRes(if (isOpen) R.color.white else R.color.black)
        divider.isVisible = !isOpen
        root.background  = ContextCompat.getDrawable(context, if (isOpen) R.color.schedule_bg_item_stadium else R.color.white)
    }

    fun setTimes(list: List<TimeScheduleUI>){
        adapterTime.setData(list)
    }

    fun setOnOpenClickListener(callback: ()->Unit){
        onOpenClickListener = callback
    }

    fun setOnTimeClickListener(callback: (TimeScheduleUI) -> Unit){
        onTimeClickListener = callback
    }
}
