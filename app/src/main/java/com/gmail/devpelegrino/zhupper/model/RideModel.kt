package com.gmail.devpelegrino.zhupper.model

data class RideModel(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Number,
    val duration: String,
    val driver: DriverModel,
    val value: Number
)
