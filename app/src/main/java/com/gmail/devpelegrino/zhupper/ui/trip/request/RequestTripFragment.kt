package com.gmail.devpelegrino.zhupper.ui.trip.request

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.devpelegrino.zhupper.R

class RequestTripFragment : Fragment() {

    companion object {
        fun newInstance() = RequestTripFragment()
    }

    private val viewModel: RequestTripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_request_trip, container, false)
    }
}