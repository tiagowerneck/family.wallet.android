package com.itau.family.ui.common

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.itau.family.R


fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Toast.showToast(activity: Activity, message: String, success: Boolean)
{
    val layout = activity.layoutInflater.inflate (
        R.layout.view_toast,
        null
    )

    val viewToast = layout.findViewById<View>(R.id.viewToast)
    if(success){
        viewToast.setBackgroundResource(R.drawable.app_widget_toast_success)
    }
    else{
        viewToast.setBackgroundResource(R.drawable.app_widget_toast_error)
    }

    val textView = layout.findViewById<TextView>(R.id.lblToast)
    textView.text = message

    this.apply {
        setGravity(Gravity.TOP, 0, 200)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()