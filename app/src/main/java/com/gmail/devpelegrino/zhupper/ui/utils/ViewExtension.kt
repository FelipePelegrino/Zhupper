package com.gmail.devpelegrino.zhupper.ui.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.gmail.devpelegrino.zhupper.R

fun View.setVisibleAnimated() {
    this.visibility = View.VISIBLE
    val slideIn = AnimationUtils.loadAnimation(this.context, R.anim.slide_in_bottom)
    this.startAnimation(slideIn)
}

@Suppress("EmptyFunctionBlock")
fun View.setGoneAnimated() {
    val slideOut = AnimationUtils.loadAnimation(this.context, R.anim.slide_out_bottom)
    slideOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}

        override fun onAnimationEnd(animation: Animation) {
            this@setGoneAnimated.visibility = View.GONE
        }

        override fun onAnimationRepeat(animation: Animation) {}
    })
    this.startAnimation(slideOut)
}
