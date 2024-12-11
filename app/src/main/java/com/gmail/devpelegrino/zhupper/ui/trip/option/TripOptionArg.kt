package com.gmail.devpelegrino.zhupper.ui.trip.option

import android.os.Parcelable
import com.gmail.devpelegrino.zhupper.model.LocationModel
import com.gmail.devpelegrino.zhupper.model.OptionModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TripOptionArg(
    val userId: String,
    val sourceAddress: String,
    val destinationAddress: String,
    val distance: Number?,
    val duration: String?,
    val sourceLocation: LocationModel?,
    val destinationLocation: LocationModel?,
    val options: List<OptionModel>?
): Parcelable
