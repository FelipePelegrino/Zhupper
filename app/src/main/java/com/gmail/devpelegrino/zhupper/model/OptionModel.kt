package com.gmail.devpelegrino.zhupper.model

data class OptionModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val vehicle: String?,
    val review: ReviewModel?,
    val value: Number?
)
