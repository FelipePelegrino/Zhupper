package com.gmail.devpelegrino.zhupper.network

import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestConfirmRideBody
import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestEstimateRideBody
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseConfirmRide
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseEstimateRide
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseRideHistory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RideApi {

    @POST("/ride/estimate")
    suspend fun requestEstimateRide(
        @Body requestBody: NetworkRequestEstimateRideBody
    ): Response<NetworkResponseEstimateRide>

    @PATCH("/ride/confirm")
    suspend fun requestConfirmRide(
        @Body requestBody: NetworkRequestConfirmRideBody
    ): Response<NetworkResponseConfirmRide>

    @GET("ride/{customer_id}")
    suspend fun fetchRideHistory(
        @Path("customer_id") customerId: String?,
        @Query("driver_id") driverId: List<Int?>
    ): Response<NetworkResponseRideHistory>
}
