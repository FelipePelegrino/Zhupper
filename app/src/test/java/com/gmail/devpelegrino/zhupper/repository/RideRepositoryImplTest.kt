package com.gmail.devpelegrino.zhupper.repository

import com.gmail.devpelegrino.zhupper.mapper.toModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.network.RideApi
import com.gmail.devpelegrino.zhupper.network.model.NetworkDriver
import com.gmail.devpelegrino.zhupper.network.model.NetworkError
import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestConfirmRideBody
import com.gmail.devpelegrino.zhupper.network.model.NetworkRequestEstimateRideBody
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseConfirmRide
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseEstimateRide
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseEstimateRideFactory
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseRideHistory
import com.gmail.devpelegrino.zhupper.network.model.NetworkResponseRideHistoryFactory
import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class RideRepositoryImplTest {

    private var rideApi: RideApi = mock()
    private var gson: Gson = mock()
    private lateinit var rideRepository: RideRepositoryImpl

    @Before
    fun setUp() {
        gson = Gson()
        Dispatchers.setMain(Dispatchers.Unconfined)
        rideRepository = RideRepositoryImpl(Dispatchers.Unconfined, gson, rideApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test requestEstimateRide success`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val mockNetworkEstimateRideModel = NetworkResponseEstimateRideFactory.createFull()
        val mockResponse: Response<NetworkResponseEstimateRide> = mock()
        val mockEstimateRideModel = mockNetworkEstimateRideModel.toModel()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )

        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(mockNetworkEstimateRideModel)
        `when`(rideApi.requestEstimateRide(networkRequestEstimateRideBody)).thenReturn(mockResponse)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.Success)
        assert((result as RepositoryResult.Success).data == mockEstimateRideModel)
    }

    @Test
    fun `test requestConfirmRide success`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val distance = RandomDataGenerator.randomInt()
        val duration = RandomDataGenerator.randomString()
        val driverId = RandomDataGenerator.randomInt()
        val driverName = RandomDataGenerator.randomString()
        val value = RandomDataGenerator.randomDouble()
        val mockResponse: Response<NetworkResponseConfirmRide> = mock()
        val networkRequestConfirmRideBody = NetworkRequestConfirmRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination,
            distance = distance,
            driver = NetworkDriver(
                id = driverId,
                name = driverName
            ),
            duration = duration,
            value = value
        )

        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(NetworkResponseConfirmRide(true))
        `when`(rideApi.requestConfirmRide(networkRequestConfirmRideBody)).thenReturn(mockResponse)

        val result = rideRepository.requestConfirmRide(
            customerId,
            origin,
            destination,
            distance,
            duration,
            driverId,
            driverName,
            value
        )

        assert(result is RepositoryResult.Success)
        assert((result as RepositoryResult.Success).data)
    }

    @Test
    fun `test getRideHistory success`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val driverIds = listOf(1, 2, 3)
        val mockNetworkRideHistory = NetworkResponseRideHistoryFactory.createFull()
        val mockRideHistoryModel = mockNetworkRideHistory.rides.map {
            it?.toModel()
        }

        val mockResponse: Response<NetworkResponseRideHistory> = mock()
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(mockNetworkRideHistory)
        `when`(rideApi.fetchRideHistory(customerId, driverIds)).thenReturn(mockResponse)

        val result = rideRepository.getRideHistory(customerId, driverIds)

        assert(result is RepositoryResult.Success)
        assert((result as RepositoryResult.Success).data == mockRideHistoryModel)
    }

    @Test
    fun `test requestEstimateRide response body null unexpected error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val mockResponse: Response<NetworkResponseEstimateRide> = mock()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )

        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(null)
        `when`(rideApi.requestEstimateRide(networkRequestEstimateRideBody)).thenReturn(mockResponse)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.UnexpectedError)
    }

    @Test
    fun `test requestEstimateRide api error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val mockError = NetworkError("404", "Not Found")
        val mockResponse: Response<NetworkResponseEstimateRide> = mock()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )
        val errorBody = ResponseBody.create(
            "application/json".toMediaType(),
            gson.toJson(mockError)
        )

        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockResponse.errorBody()).thenReturn(errorBody)
        `when`(rideApi.requestEstimateRide(networkRequestEstimateRideBody)).thenReturn(mockResponse)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.ApiError)
        assert((result as RepositoryResult.ApiError).errorCode == "404")
    }

    @Test
    fun `test requestEstimateRide throw UnknownHostException network error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )

        doAnswer { throw UnknownHostException() }
            .`when`(rideApi).requestEstimateRide(networkRequestEstimateRideBody)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.NetworkError)
    }

    @Test
    fun `test requestEstimateRide throw SocketTimeoutException network error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )

        doAnswer { throw SocketTimeoutException() }
            .`when`(rideApi).requestEstimateRide(networkRequestEstimateRideBody)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.NetworkError)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `test requestEstimateRide throw generic Exception unexpected error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )

        doAnswer { throw Exception() }
            .`when`(rideApi).requestEstimateRide(networkRequestEstimateRideBody)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.UnexpectedError)
    }

    @Test
    fun `test requestEstimateRide unexpected error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        val networkRequestEstimateRideBody = NetworkRequestEstimateRideBody(
            customerId = customerId,
            origin = origin,
            destination = destination
        )

        doAnswer { throw HttpException(mock()) }
            .`when`(rideApi).requestEstimateRide(networkRequestEstimateRideBody)

        val result = rideRepository.requestEstimateRide(customerId, origin, destination)

        assert(result is RepositoryResult.UnexpectedError)
    }
}