package com.gmail.devpelegrino.zhupper.model

data class EstimateRideModel(
    val origin: LocationModel,
    val destination: LocationModel,
    val distance: Double,
    val duration: String,
    val options: List<OptionModel>,
    val routeResponse: Any
)
