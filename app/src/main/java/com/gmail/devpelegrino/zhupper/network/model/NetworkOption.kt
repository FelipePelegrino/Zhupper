package com.gmail.devpelegrino.zhupper.network.model

data class NetworkOption(
    val id: Int,
    val name: String?,
    val description: String?,
    val vehicle: String?,
    val review: NetworkReview?,
    val value: Double?
)
