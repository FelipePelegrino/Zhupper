package com.gmail.devpelegrino.zhupper.mapper

import com.gmail.devpelegrino.zhupper.model.OptionModel
import com.gmail.devpelegrino.zhupper.network.model.NetworkOption

fun NetworkOption.toModel() = OptionModel(
    id = id,
    name = name,
    description = description,
    vehicle = vehicle,
    review = review?.toModel(),
    value = value
)
