package com.gmail.devpelegrino.zhupper.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRequestEstimateRideBody(
    @SerializedName("customer_id")
    val customerId: String?,
    val origin: String?,
    val destination: String?
)
