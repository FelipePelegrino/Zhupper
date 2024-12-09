package com.gmail.devpelegrino.zhupper.ui.trip.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.devpelegrino.zhupper.model.EstimateRideModel
import com.gmail.devpelegrino.zhupper.model.RepositoryResult
import com.gmail.devpelegrino.zhupper.repository.RideRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RequestTripViewModel(
    private val rideRepository: RideRepository
) : ViewModel() {

    companion object {
        private const val MIN_LOADING_TIME = 750L
    }

    private var _userIdTextState: String = ""
    val userIdTextState: String get() = _userIdTextState

    private var _sourceAddressTextState: String = ""
    val sourceAddressTextState: String get() = _sourceAddressTextState

    private var _destinyAddressTextState: String = ""
    val destinyAddressTextState: String get() = _destinyAddressTextState

    private val _uiState = MutableStateFlow<RequestTripUiState>(RequestTripUiState.Idle)
    val uiState: StateFlow<RequestTripUiState> get() = _uiState

    fun cleanUiState() {
        _uiState.value = RequestTripUiState.Idle
    }

    fun updateUserIdTextState(text: String) {
        _userIdTextState = text
    }

    fun updateSourceAddressTextState(text: String) {
        _sourceAddressTextState = text
    }

    fun updateDestinyAddressTextState(text: String) {
        _destinyAddressTextState = text
    }

    fun requestRideTest(
        customerId: String?,
        origin: String?,
        destination: String?
    ) {
        viewModelScope.launch {
            _uiState.value = RequestTripUiState.Loading
            delay(MIN_LOADING_TIME)

            val result = rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )

            _uiState.value = RequestTripUiState.Loaded

            when (result) {
                is RepositoryResult.Success -> {
                    _uiState.value = RequestTripUiState.Success(estimateRideModel = result.data)
                }

                is RepositoryResult.ApiError -> {
                    _uiState.value = RequestTripUiState.ApiError(
                        errorCode = result.errorCode,
                        errorDescription = result.errorDescription
                    )

                }

                RepositoryResult.NetworkError -> {
                    _uiState.value = RequestTripUiState.NetworkError
                }

                RepositoryResult.UnexpectedError -> {
                    _uiState.value = RequestTripUiState.UnexpectedError
                }
            }
        }
    }

    sealed class RequestTripUiState {
        data class Success(
            val estimateRideModel: EstimateRideModel
        ) : RequestTripUiState()

        data class ApiError(
            val errorCode: String,
            val errorDescription: String
        ) : RequestTripUiState()

        data object Loading : RequestTripUiState()
        data object Loaded : RequestTripUiState()
        data object UnexpectedError : RequestTripUiState()
        data object NetworkError : RequestTripUiState()
        data object Idle : RequestTripUiState()
    }
}
