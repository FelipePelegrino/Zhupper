package com.gmail.devpelegrino.zhupper.network.model

data class NetworkResponseEstimateRide(
    val origin: NetworkLocation?,
    val destination: NetworkLocation?,
    val distance: Number?,
    val duration: String?,
    val options: List<NetworkOption>?
)
