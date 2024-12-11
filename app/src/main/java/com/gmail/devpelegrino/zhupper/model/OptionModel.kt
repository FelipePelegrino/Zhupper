package com.gmail.devpelegrino.zhupper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionModel(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: ReviewModel?,
    val value: Number
): Parcelable
