package com.gmail.devpelegrino.zhupper.mapper

import com.gmail.devpelegrino.zhupper.model.LocationModel
import com.gmail.devpelegrino.zhupper.network.model.NetworkLocation

fun NetworkLocation.toModel() = LocationModel(
    latitude = latitude,
    longitude = longitude
)
