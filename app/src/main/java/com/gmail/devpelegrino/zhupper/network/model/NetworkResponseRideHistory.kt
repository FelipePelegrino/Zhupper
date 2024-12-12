package com.gmail.devpelegrino.zhupper.network.model

import com.google.gson.annotations.SerializedName

data class NetworkResponseRideHistory(
    @SerializedName("customer_id")
    val customerId: String,
    val rides: List<NetworkRide?>
)
