package com.gmail.devpelegrino.zhupper.mapper

import com.gmail.devpelegrino.zhupper.model.EstimateRideModel
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseEstimateRide

fun NetworkResponseEstimateRide.toModel() = EstimateRideModel(
    origin = origin?.toModel(),
    destination = destination?.toModel(),
    distance = distance,
    duration = duration,
    options = options?.map { it.toModel() },
    routeResponse = routeResponse
)
