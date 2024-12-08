package com.gmail.devpelegrino.zhupper.network.model

data class NetworkResponseEstimateRide(
    val origin: NetworkLocation,
    val destination: NetworkLocation,
    val distance: Double,
    val duration: String,
    val options: List<NetworkOption>,
    val routeResponse: Any
)
