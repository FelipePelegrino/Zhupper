@file:Suppress("LongParameterList")

package com.gmail.devpelegrino.zhupper.ui.trip.option

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.devpelegrino.zhupper.model.OptionModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.repository.RideRepository
import com.gmail.devpelegrino.zhupper.ui.utils.MIN_LOADING_TIME
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripOptionViewModel(
    private val rideRepository: RideRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TripOptionUiState>(TripOptionUiState.Idle)
    val uiState: StateFlow<TripOptionUiState> get() = _uiState

    fun cleanUiState() {
        _uiState.value = TripOptionUiState.Idle
    }

    fun chooseOption(
        userId: String,
        sourceAddress: String,
        destinationAddress: String,
        distance: Number?,
        duration: String?,
        option: OptionModel
    ) {
        viewModelScope.launch {
            _uiState.value = TripOptionUiState.Loading
            delay(MIN_LOADING_TIME)

            val result = rideRepository.requestConfirmRide(
                customerId = userId,
                origin = sourceAddress,
                destination = destinationAddress,
                distance = distance,
                duration = duration,
                driverId = option.id,
                driverName = option.name,
                value = option.value
            )

            _uiState.value = TripOptionUiState.Loaded

            when (result) {
                is RepositoryResult.Success -> {
                    _uiState.value = TripOptionUiState.Success
                }

                is RepositoryResult.ApiError -> {
                    _uiState.value = TripOptionUiState.ApiError(
                        errorCode = result.errorCode,
                        errorDescription = result.errorDescription
                    )

                }

                RepositoryResult.NetworkError -> {
                    _uiState.value = TripOptionUiState.NetworkError
                }

                RepositoryResult.UnexpectedError -> {
                    _uiState.value = TripOptionUiState.UnexpectedError
                }
            }
        }
    }

    sealed class TripOptionUiState {
        data object Success : TripOptionUiState()

        data class ApiError(
            val errorCode: String,
            val errorDescription: String
        ) : TripOptionUiState()

        data object NetworkError : TripOptionUiState()
        data object UnexpectedError : TripOptionUiState()
        data object Loading : TripOptionUiState()
        data object Loaded : TripOptionUiState()
        data object Idle : TripOptionUiState()
    }
}