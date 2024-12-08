package com.gmail.devpelegrino.zhupper.repository

import android.util.Log
import com.gmail.devpelegrino.zhupper.mapper.toModel
import com.gmail.devpelegrino.zhupper.model.EstimateRideModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.network.RideApi
import com.gmail.devpelegrino.zhupper.network.model.NetworkError
import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestEstimateRideBody
import com.google.gson.Gson
import retrofit2.HttpException

class RideRepositoryImpl(
    private val rideApi: RideApi,
    private val gson: Gson
) : RideRepository {

    override suspend fun requestEstimateRide(
        customerId: String?,
        origin: String?,
        destination: String?
    ): RepositoryResult<EstimateRideModel> {
        return try {
            val response = rideApi.requestEstimateRide(
                NetworkRequestEstimateRideBody(
                    customerId = customerId,
                    origin = origin,
                    destination = destination
                )
            )

            if (response.isSuccessful) {
                RepositoryResult.Success(response.body()!!.toModel())
            } else {
                val errorBody = response.errorBody()?.string()
                val networkError = gson.fromJson(errorBody, NetworkError::class.java)
                RepositoryResult.ApiError(
                    errorCode = networkError.errorCode,
                    errorDescription = networkError.errorDescription
                )
            }
        } catch (e: HttpException) {
            Log.e(RideRepositoryImpl::class.java.simpleName, "HttpException: $e")
            RepositoryResult.UnexpectedError(e.message ?: "Unknown error")
        } catch (e: Exception) {
            Log.e(RideRepositoryImpl::class.java.simpleName, "Generic exception: $e")
            RepositoryResult.UnexpectedError(e.message ?: "Unknown error")
        }
    }
}
