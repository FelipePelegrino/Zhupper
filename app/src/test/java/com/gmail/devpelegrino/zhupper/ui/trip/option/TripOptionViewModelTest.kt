package com.gmail.devpelegrino.zhupper.ui.trip.option

import app.cash.turbine.test
import com.gmail.devpelegrino.zhupper.model.OptionModelFactory
import com.gmail.devpelegrino.zhupper.model.RepositoryResultFactory
import com.gmail.devpelegrino.zhupper.repository.RideRepository
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
class TripOptionViewModelTest {

    private val rideRepository: RideRepository = mock()
    private lateinit var viewModel: TripOptionViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = TripOptionViewModel(rideRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test cleanUiState sets uiState to Idle`() = runTest {
        viewModel.cleanUiState()
        assertEquals(TripOptionViewModel.TripOptionUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `test chooseOption success`() = runTest {
        val userId = RandomDataGenerator.randomString()
        val sourceAddress = RandomDataGenerator.randomString()
        val destinationAddress = RandomDataGenerator.randomString()
        val distance = RandomDataGenerator.randomInt()
        val duration = RandomDataGenerator.randomString()
        val option = OptionModelFactory.createFull()

        val repositorySuccess = RepositoryResultFactory.createSuccess(true)

        `when`(
            rideRepository.requestConfirmRide(
                customerId = userId,
                origin = sourceAddress,
                destination = destinationAddress,
                distance = distance,
                duration = duration,
                driverId = option.id,
                driverName = option.name,
                value = option.value
            )
        ).thenReturn(repositorySuccess)

        viewModel.uiState.test {
            viewModel.chooseOption(
                userId,
                sourceAddress,
                destinationAddress,
                distance,
                duration,
                option
            )

            assertEquals(TripOptionViewModel.TripOptionUiState.Idle, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripOptionViewModel.TripOptionUiState.Loaded, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.Success, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test chooseOption api error`() = runTest {
        val userId = RandomDataGenerator.randomString()
        val sourceAddress = RandomDataGenerator.randomString()
        val destinationAddress = RandomDataGenerator.randomString()
        val distance = RandomDataGenerator.randomInt()
        val duration = RandomDataGenerator.randomString()
        val option = OptionModelFactory.createFull()

        val repositoryApiError = RepositoryResultFactory.createApiError("404", "Not Found")

        `when`(
            rideRepository.requestConfirmRide(
                customerId = userId,
                origin = sourceAddress,
                destination = destinationAddress,
                distance = distance,
                duration = duration,
                driverId = option.id,
                driverName = option.name,
                value = option.value
            )
        ).thenReturn(repositoryApiError)

        viewModel.uiState.test {
            viewModel.chooseOption(
                userId,
                sourceAddress,
                destinationAddress,
                distance,
                duration,
                option
            )

            assertEquals(TripOptionViewModel.TripOptionUiState.Idle, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripOptionViewModel.TripOptionUiState.Loaded, awaitItem())
            assertEquals(
                TripOptionViewModel.TripOptionUiState.ApiError("404", "Not Found"),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test chooseOption network error`() = runTest {
        val userId = RandomDataGenerator.randomString()
        val sourceAddress = RandomDataGenerator.randomString()
        val destinationAddress = RandomDataGenerator.randomString()
        val distance = RandomDataGenerator.randomInt()
        val duration = RandomDataGenerator.randomString()
        val option = OptionModelFactory.createFull()

        val repositoryNetworkError = RepositoryResultFactory.createNetworkError()

        `when`(
            rideRepository.requestConfirmRide(
                customerId = userId,
                origin = sourceAddress,
                destination = destinationAddress,
                distance = distance,
                duration = duration,
                driverId = option.id,
                driverName = option.name,
                value = option.value
            )
        ).thenReturn(repositoryNetworkError)

        viewModel.uiState.test {
            viewModel.chooseOption(
                userId,
                sourceAddress,
                destinationAddress,
                distance,
                duration,
                option
            )

            assertEquals(TripOptionViewModel.TripOptionUiState.Idle, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripOptionViewModel.TripOptionUiState.Loaded, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.NetworkError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test chooseOption unexpected error`() = runTest {
        val userId = RandomDataGenerator.randomString()
        val sourceAddress = RandomDataGenerator.randomString()
        val destinationAddress = RandomDataGenerator.randomString()
        val distance = RandomDataGenerator.randomInt()
        val duration = RandomDataGenerator.randomString()
        val option = OptionModelFactory.createFull()

        val repositoryUnexpectedError = RepositoryResultFactory.createUnexpectedError()

        `when`(
            rideRepository.requestConfirmRide(
                customerId = userId,
                origin = sourceAddress,
                destination = destinationAddress,
                distance = distance,
                duration = duration,
                driverId = option.id,
                driverName = option.name,
                value = option.value
            )
        ).thenReturn(repositoryUnexpectedError)

        viewModel.uiState.test {
            viewModel.chooseOption(
                userId,
                sourceAddress,
                destinationAddress,
                distance,
                duration,
                option
            )

            assertEquals(TripOptionViewModel.TripOptionUiState.Idle, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.Loading, awaitItem())
            delay(MIN_LOADING_TIME)
            assertEquals(TripOptionViewModel.TripOptionUiState.Loaded, awaitItem())
            assertEquals(TripOptionViewModel.TripOptionUiState.UnexpectedError, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        const val MIN_LOADING_TIME = 1000L
    }
}
