@file:Suppress("LongParameterList")

package com.gmail.devpelegrino.zhupper.repository

import com.gmail.devpelegrino.zhupper.model.EstimateRideModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.model.RideModel

interface RideRepository {

    suspend fun requestEstimateRide(
        customerId: String?,
        origin: String?,
        destination: String?
    ): RepositoryResult<EstimateRideModel>

    suspend fun requestConfirmRide(
        customerId: String?,
        origin: String?,
        destination: String?,
        distance: Number?,
        duration: String?,
        driverId: Int,
        driverName: String,
        value: Number
    ): RepositoryResult<Boolean>

    suspend fun getRideHistory(
        customerId: String?,
        driverId: Int?
    ): RepositoryResult<List<RideModel?>>
}
