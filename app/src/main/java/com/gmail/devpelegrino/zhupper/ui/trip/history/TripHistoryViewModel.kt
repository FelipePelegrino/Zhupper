package com.gmail.devpelegrino.zhupper.ui.trip.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.model.RideModel
import com.gmail.devpelegrino.zhupper.repository.RideRepository
import com.gmail.devpelegrino.zhupper.ui.utils.MIN_LOADING_TIME
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripHistoryViewModel(
    private val rideRepository: RideRepository
) : ViewModel() {

    companion object {
        const val SPINNER_ALL_DRIVERS = 0
        const val SPINNER_DRIVER_ID_ONE = 1
        const val SPINNER_DRIVER_ID_TWO = 2
        const val SPINNER_DRIVER_ID_THREE = 3
    }

    private val driverSpinnerData by lazy {
        listOf(
            SPINNER_ALL_DRIVERS,
            SPINNER_DRIVER_ID_ONE,
            SPINNER_DRIVER_ID_TWO,
            SPINNER_DRIVER_ID_THREE
        )
    }

    private val _uiState = MutableStateFlow<TripHistoryUiState>(TripHistoryUiState.Idle)
    val uiState: StateFlow<TripHistoryUiState> get() = _uiState

    fun cleanUiState() {
        _uiState.value = TripHistoryUiState.Idle
    }

    fun getDriverData(): List<Int> {
        return driverSpinnerData
    }

    fun getRideHistory(
        customerId: String?,
        spinnerPosition: Int
    ) {
        viewModelScope.launch {
            if (customerId.isNullOrEmpty()) {
                _uiState.value = TripHistoryUiState.CheckUserIdError
            } else {
                _uiState.value = TripHistoryUiState.Loading
                delay(MIN_LOADING_TIME)

                val result = if (spinnerPosition == SPINNER_ALL_DRIVERS) {
                    rideRepository.getRideHistory(
                        customerId = customerId,
                        driverId = listOf(
                            SPINNER_DRIVER_ID_ONE,
                            SPINNER_DRIVER_ID_TWO,
                            SPINNER_DRIVER_ID_THREE
                        )
                    )
                } else {
                    rideRepository.getRideHistory(
                        customerId = customerId,
                        driverId = listOf(spinnerPosition)
                    )
                }

                _uiState.value = TripHistoryUiState.Loaded

                when (result) {
                    is RepositoryResult.Success -> {
                        _uiState.value = TripHistoryUiState.Success(
                            rides = result.data
                        )
                    }

                    is RepositoryResult.ApiError -> {
                        _uiState.value = TripHistoryUiState.ApiError(
                            errorCode = result.errorCode,
                            errorDescription = result.errorDescription
                        )

                    }

                    RepositoryResult.NetworkError -> {
                        _uiState.value = TripHistoryUiState.NetworkError
                    }

                    RepositoryResult.UnexpectedError -> {
                        _uiState.value = TripHistoryUiState.UnexpectedError
                    }
                }
            }
        }
    }

    sealed class TripHistoryUiState {
        data class Success(
            val rides: List<RideModel?>
        ) : TripHistoryUiState()

        data class ApiError(
            val errorCode: String,
            val errorDescription: String
        ) : TripHistoryUiState()

        data object CheckUserIdError : TripHistoryUiState()
        data object NetworkError : TripHistoryUiState()
        data object UnexpectedError : TripHistoryUiState()
        data object Loading : TripHistoryUiState()
        data object Loaded : TripHistoryUiState()
        data object Idle : TripHistoryUiState()
    }
}