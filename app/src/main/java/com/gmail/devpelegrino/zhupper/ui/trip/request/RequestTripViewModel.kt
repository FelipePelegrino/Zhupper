package com.gmail.devpelegrino.zhupper.ui.trip.request

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.devpelegrino.zhupper.repository.RideRepository
import kotlinx.coroutines.launch

class RequestTripViewModel(
    private val rideRepository: RideRepository
) : ViewModel() {

    fun requestRideTest(
        customerId: String?,
        origin: String?,
        destination: String?
    ) {
        Log.i("Teste", "customerId: $customerId")
        Log.i("Teste", "origin: $origin")
        Log.i("Teste", "destination: $destination")
        viewModelScope.launch {
            val result = rideRepository.requestEstimateRide(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
        }
    }
}
