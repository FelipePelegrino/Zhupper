package com.gmail.devpelegrino.zhupper.repository

import com.gmail.devpelegrino.zhupper.model.EstimateRideModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult

interface RideRepository {

    suspend fun requestEstimateRide(
        customerId: String?,
        origin: String?,
        destination: String?
    ): RepositoryResult<EstimateRideModel>
}
