package com.gmail.devpelegrino.zhupper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel(
    val latitude: Number?,
    val longitude: Number?
): Parcelable
