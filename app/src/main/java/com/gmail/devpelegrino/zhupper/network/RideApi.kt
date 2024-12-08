package com.gmail.devpelegrino.zhupper.network

import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestEstimateRideBody
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseEstimateRide
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RideApi {

    @POST("/ride/estimate")
    suspend fun requestEstimateRide(
        @Body requestBody: NetworkRequestEstimateRideBody
    ): Response<NetworkResponseEstimateRide>
}
