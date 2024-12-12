package com.gmail.devpelegrino.zhupper.mapper

import com.gmail.devpelegrino.zhupper.model.DriverModel
import com.gmail.devpelegrino.zhupper.network.model.NetworkDriver

fun NetworkDriver.toModel() = DriverModel(
    id = id,
    name = name
)
