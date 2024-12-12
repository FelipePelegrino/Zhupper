package com.gmail.devpelegrino.zhupper.mapper

import com.gmail.devpelegrino.zhupper.model.RideModel
import com.gmail.devpelegrino.zhupper.network.model.NetworkRide

fun NetworkRide.toModel() = RideModel(
    id = id,
    date = date,
    origin = origin,
    destination = destination,
    distance = distance,
    duration = duration,
    driver = driver.toModel(),
    value = value
)
