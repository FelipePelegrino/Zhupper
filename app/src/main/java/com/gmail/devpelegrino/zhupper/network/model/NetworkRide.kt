package com.gmail.devpelegrino.zhupper.network.model

data class NetworkRide(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Number,
    val duration: String,
    val driver: NetworkDriver,
    val value: Number
)
