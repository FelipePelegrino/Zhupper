package com.gmail.devpelegrino.zhupper.ui.trip.request

import app.cash.turbine.test
import com.gmail.devpelegrino.zhupper.model.EstimateRideModelFactory
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.model.RepositoryResultFactory
import com.gmail.devpelegrino.zhupper.repository.RideRepository
import com.gmail.devpelegrino.zhupper.utils.MIN_LOADING_TIME
import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class RequestTripViewModelTest {

    private val rideRepository: RideRepository = mock()
    private lateinit var viewModel: RequestTripViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = RequestTripViewModel(rideRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test cleanUiState sets uiState to Idle`() = runTest {
        viewModel.cleanUiState()
        assertEquals(RequestTripViewModel.RequestTripUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `test updateUserIdTextState updates state`() {
        val userId = RandomDataGenerator.randomString()
        viewModel.updateUserIdTextState(userId)
        assertEquals(userId, viewModel.userIdTextState)
    }

    @Test
    fun `test updateSourceAddressTextState updates state`() {
        val address = RandomDataGenerator.randomString()
        viewModel.updateSourceAddressTextState(address)
        assertEquals(address, viewModel.sourceAddressTextState)
    }

    @Test
    fun `test updateDestinationAddressTextState updates state`() {
        val address = RandomDataGenerator.randomString()
        viewModel.updateDestinationAddressTextState(address)
        assertEquals(address, viewModel.destinationAddressTextState)
    }

    @Test
    fun `test requestEstimateRide success`() = runTest {
        val mockFullEstimateRideModel = EstimateRideModelFactory.createFull()
        val repositorySuccess = RepositoryResultFactory.createSuccess(mockFullEstimateRideModel)
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()

        `when`(
            rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
        ).thenReturn(repositorySuccess)

        viewModel.uiState.test {
            viewModel.requestEstimateRide(customerId, origin, destination)

            assertEquals(RequestTripViewModel.RequestTripUiState.Idle, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(RequestTripViewModel.RequestTripUiState.Loaded, awaitItem())
            assertEquals(
                RequestTripViewModel.RequestTripUiState.Success(mockFullEstimateRideModel),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test requestEstimateRide empty data error`() = runTest {
        val mockEmptyEstimateRideModel = EstimateRideModelFactory.createEmptyOptions()
        val repositorySuccess = RepositoryResultFactory.createSuccess(mockEmptyEstimateRideModel)
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()

        `when`(
            rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
        ).thenReturn(repositorySuccess)

        viewModel.uiState.test {
            viewModel.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )

            assertEquals(RequestTripViewModel.RequestTripUiState.Idle, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(RequestTripViewModel.RequestTripUiState.Loaded, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.EmptyDataError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test requestEstimateRide check input data error`() = runTest {
        val customerId = listOf(
            "",
            null
        ).shuffled().first()
        val origin = listOf(
            "",
            null
        ).shuffled().first()
        val destination = listOf(
            "",
            null
        ).shuffled().first()

        viewModel.uiState.test {
            viewModel.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )

            assertEquals(RequestTripViewModel.RequestTripUiState.Idle, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.CheckInputDataError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test requestEstimateRide api error`() = runTest {
        val repositoryApiError = RepositoryResult.ApiError(
            "404",
            "Not Found"
        )
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()

        `when`(
            rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
        ).thenReturn(repositoryApiError)

        viewModel.uiState.test {
            viewModel.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )

            assertEquals(RequestTripViewModel.RequestTripUiState.Idle, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(RequestTripViewModel.RequestTripUiState.Loaded, awaitItem())
            assertEquals(
                RequestTripViewModel.RequestTripUiState.ApiError(
                    "404",
                    "Not Found"
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test requestEstimateRide network error`() = runTest {
        val repositoryNetworkError = RepositoryResult.NetworkError
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        `when`(
            rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
        ).thenReturn(repositoryNetworkError)

        viewModel.uiState.test {
            viewModel.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )

            assertEquals(RequestTripViewModel.RequestTripUiState.Idle, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(RequestTripViewModel.RequestTripUiState.Loaded, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.NetworkError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test requestEstimateRide unexpected error`() = runTest {
        val repositoryUnexpectedError = RepositoryResult.UnexpectedError
        val customerId = RandomDataGenerator.randomString()
        val origin = RandomDataGenerator.randomString()
        val destination = RandomDataGenerator.randomString()
        `when`(
            rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
        ).thenReturn(repositoryUnexpectedError)

        viewModel.uiState.test {
            viewModel.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )

            assertEquals(RequestTripViewModel.RequestTripUiState.Idle, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(RequestTripViewModel.RequestTripUiState.Loaded, awaitItem())
            assertEquals(RequestTripViewModel.RequestTripUiState.UnexpectedError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}