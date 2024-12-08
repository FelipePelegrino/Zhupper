package com.gmail.devpelegrino.zhupper.ui.trip.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.devpelegrino.zhupper.R

class TripHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = TripHistoryFragment()
    }

    private val viewModel: TripHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_trip_history, container, false)
    }
}