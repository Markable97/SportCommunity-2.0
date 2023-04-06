package com.glushko.sportcommunity.util.extensions

import android.app.Activity
import android.graphics.Typeface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.glushko.sportcommunity.R
import com.google.android.material.snackbar.Snackbar

private const val MIN_CLICK_DELAY = 500L


fun View.setSafeOnClickListener(action: (View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View) {
            if (lastClickTime + MIN_CLICK_DELAY < SystemClock.elapsedRealtime()) {
                lastClickTime = SystemClock.elapsedRealtime()
                action(v)
            }
        }
    })
}

fun View.showDebugUnderDevelopmentMessage() = Snackbar.make(
    this,
    R.string.debug_under_development,
    Snackbar.LENGTH_LONG
).show()

fun View.showGone(show: Boolean): View {
    visibility = if (show) View.VISIBLE else View.GONE
    return this
}

fun View.showInvisible(show: Boolean): View {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
    return this
}

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.invisible(): View {
    visibility = View.INVISIBLE
    return this
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

fun View.enableDisable(enable: Boolean): View {
    return if (enable) this.enable() else this.disable()
}

fun View.enable(): View {
    isEnabled = true
    alpha = 1f
    isClickable = true
    return this
}

fun View.disable(): View {
    isEnabled = false
    alpha = 0.5f
    isClickable = false
    return this
}


internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(ContextCompat.getColor(context, color))

fun TextView.setSelection() {
    typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
    setTextColor(ContextCompat.getColor(context, R.color.bg_tournament_table_team_selected))
}

fun View.hideKeyboardFrom() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

