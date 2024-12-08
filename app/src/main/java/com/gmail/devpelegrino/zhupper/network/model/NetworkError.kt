package com.gmail.devpelegrino.zhupper.network.model

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("error_description")
    val errorDescription: String
)
