package com.gmail.devpelegrino.zhupper.mapper

import com.gmail.devpelegrino.zhupper.model.ReviewModel
import com.gmail.devpelegrino.zhupper.network.model.NetworkReview

fun NetworkReview.toModel() = ReviewModel(
    rating = rating,
    comment = comment
)
