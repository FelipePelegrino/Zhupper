package com.gmail.devpelegrino.zhupper.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRequestConfirmRideBody(
    @SerializedName("customer_id")
    val customerId: String?,
    val origin: String?,
    val destination: String?,
    val distance: Number?,
    val duration: String,
    val driver: NetworkDriver,
    val value: Number
)
