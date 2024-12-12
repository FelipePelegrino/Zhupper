package com.gmail.devpelegrino.zhupper.ui.utils

import android.view.View
import com.google.android.material.button.MaterialButton

private const val MIN_CLICK_INTERVAL: Long = 500

fun MaterialButton.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    var lastClickTime = 0L

    this.setOnClickListener { view ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_INTERVAL) {
            lastClickTime = currentTime
            onSafeClick(view)
        }
    }
}
