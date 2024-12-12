package com.gmail.devpelegrino.zhupper.ui.trip.history

import app.cash.turbine.test
import com.gmail.devpelegrino.zhupper.model.RepositoryResultFactory
import com.gmail.devpelegrino.zhupper.model.RideModelFactory
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
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class TripHistoryViewModelTest {

    private val rideRepository: RideRepository = mock()
    private lateinit var viewModel: TripHistoryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = TripHistoryViewModel(rideRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test cleanUiState sets uiState to Idle`() = runTest {
        viewModel.cleanUiState()
        assertEquals(TripHistoryViewModel.TripHistoryUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `test getDriverData returns correct list of drivers`() {
        val expectedDriverData = listOf(
            TripHistoryViewModel.SPINNER_ALL_DRIVERS,
            TripHistoryViewModel.SPINNER_DRIVER_ID_ONE,
            TripHistoryViewModel.SPINNER_DRIVER_ID_TWO,
            TripHistoryViewModel.SPINNER_DRIVER_ID_THREE
        )

        assertEquals(expectedDriverData, viewModel.getDriverData())
    }

    @Test
    fun `test getRideHistory for all drivers success`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val numberOfRides = Random.nextInt(1, 6)
        val mockRides = List(numberOfRides) { RideModelFactory.createFull() }
        val repositorySuccess = RepositoryResultFactory.createSuccess(mockRides)

        `when`(
            rideRepository.getRideHistory(
                customerId = customerId,
                driverId = listOf(
                    TripHistoryViewModel.SPINNER_DRIVER_ID_ONE,
                    TripHistoryViewModel.SPINNER_DRIVER_ID_TWO,
                    TripHistoryViewModel.SPINNER_DRIVER_ID_THREE
                )
            )
        ).thenReturn(repositorySuccess)

        viewModel.uiState.test {
            viewModel.getRideHistory(customerId, TripHistoryViewModel.SPINNER_ALL_DRIVERS)

            assertEquals(TripHistoryViewModel.TripHistoryUiState.Idle, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loaded, awaitItem())
            assertEquals(
                TripHistoryViewModel.TripHistoryUiState.Success(mockRides),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getRideHistory for a single driver success`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val numberOfRides = Random.nextInt(1, 6)
        val mockRides = List(numberOfRides) { RideModelFactory.createFull() }
        val repositorySuccess = RepositoryResultFactory.createSuccess(mockRides)

        `when`(
            rideRepository.getRideHistory(
                customerId = customerId,
                driverId = listOf(TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)
            )
        ).thenReturn(repositorySuccess)

        viewModel.uiState.test {
            viewModel.getRideHistory(customerId, TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)

            assertEquals(TripHistoryViewModel.TripHistoryUiState.Idle, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loaded, awaitItem())
            assertEquals(
                TripHistoryViewModel.TripHistoryUiState.Success(mockRides),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getRideHistory api error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val repositoryApiError = RepositoryResultFactory.createApiError("404", "Not Found")

        `when`(
            rideRepository.getRideHistory(
                customerId = customerId,
                driverId = listOf(TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)
            )
        ).thenReturn(repositoryApiError)

        viewModel.uiState.test {
            viewModel.getRideHistory(customerId, TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)

            assertEquals(TripHistoryViewModel.TripHistoryUiState.Idle, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loaded, awaitItem())
            assertEquals(
                TripHistoryViewModel.TripHistoryUiState.ApiError("404", "Not Found"),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getRideHistory network error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val repositoryNetworkError = RepositoryResultFactory.createNetworkError()

        `when`(
            rideRepository.getRideHistory(
                customerId = customerId,
                driverId = listOf(TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)
            )
        ).thenReturn(repositoryNetworkError)

        viewModel.uiState.test {
            viewModel.getRideHistory(customerId, TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)

            assertEquals(TripHistoryViewModel.TripHistoryUiState.Idle, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loaded, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.NetworkError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getRideHistory unexpected error`() = runTest {
        val customerId = RandomDataGenerator.randomString()
        val repositoryUnexpectedError = RepositoryResultFactory.createUnexpectedError()

        `when`(
            rideRepository.getRideHistory(
                customerId = customerId,
                driverId = listOf(TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)
            )
        ).thenReturn(repositoryUnexpectedError)

        viewModel.uiState.test {
            viewModel.getRideHistory(customerId, TripHistoryViewModel.SPINNER_DRIVER_ID_ONE)

            assertEquals(TripHistoryViewModel.TripHistoryUiState.Idle, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripHistoryViewModel.TripHistoryUiState.Loaded, awaitItem())
            assertEquals(TripHistoryViewModel.TripHistoryUiState.UnexpectedError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
