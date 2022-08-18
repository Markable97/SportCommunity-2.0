package com.glushko.sportcommunity.util.extensions

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.addOnPageSelectedListener(block: (position: Int) -> Unit) =
    object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) = block.invoke(position)
    }.apply(::registerOnPageChangeCallback)