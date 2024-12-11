package com.gmail.devpelegrino.zhupper.repository

import android.util.Log
import com.gmail.devpelegrino.zhupper.mapper.toModel
import com.gmail.devpelegrino.zhupper.model.EstimateRideModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.network.RideApi
import com.gmail.devpelegrino.zhupper.network.model.NetworkDriver
import com.gmail.devpelegrino.zhupper.network.model.NetworkError
import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestConfirmRideBody
import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestEstimateRideBody
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RideRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val gson: Gson,
    private val rideApi: RideApi
) : RideRepository {

    companion object {
        private const val TAG = "RideRepositoryImpl"
    }

    override suspend fun requestEstimateRide(
        customerId: String?,
        origin: String?,
        destination: String?
    ): RepositoryResult<EstimateRideModel> {
        return withContext(ioDispatcher) {
            baseApiCall(
                apiCall = {
                    rideApi.requestEstimateRide(
                        NetworkRequestEstimateRideBody(
                            customerId = customerId,
                            origin = origin,
                            destination = destination
                        )
                    )
                },
                transform = { it.toModel() }
            )
        }
    }

    override suspend fun requestConfirmRide(
        customerId: String?,
        origin: String?,
        destination: String?,
        distance: Number?,
        duration: String?,
        driverId: Int,
        driverName: String,
        value: Number
    ): RepositoryResult<Boolean> {
        return withContext(ioDispatcher) {
            baseApiCall(
                apiCall = {
                    rideApi.requestConfirmRide(
                        NetworkRequestConfirmRideBody(
                            customerId = customerId,
                            origin = origin,
                            destination = destination ?: "",
                            distance = distance ?: 0,
                            duration = duration ?: "",
                            driver = NetworkDriver(
                                id = driverId,
                                name = driverName
                            ),
                            value = value
                        )
                    )
                },
                transform = { it.success }
            )
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private suspend fun <T, R> baseApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): RepositoryResult<R> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    RepositoryResult.Success(transform(body))
                } else {
                    RepositoryResult.UnexpectedError
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val networkError = gson.fromJson(errorBody, NetworkError::class.java)
                RepositoryResult.ApiError(
                    errorCode = networkError.errorCode,
                    errorDescription = networkError.errorDescription
                )
            }
        } catch (e: UnknownHostException) {
            Log.e(TAG, e.toString())
            RepositoryResult.NetworkError
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.toString())
            RepositoryResult.NetworkError
        } catch (e: HttpException) {
            Log.e(TAG, e.toString())
            RepositoryResult.UnexpectedError
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            RepositoryResult.UnexpectedError
        }
    }
}
