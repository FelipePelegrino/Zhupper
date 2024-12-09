package com.gmail.devpelegrino.zhupper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(
    val rating: Number?,
    val comment: String?
): Parcelable
