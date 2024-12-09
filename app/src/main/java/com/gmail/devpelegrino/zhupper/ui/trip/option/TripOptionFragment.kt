package com.gmail.devpelegrino.zhupper.ui.trip.option

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.devpelegrino.zhupper.R

class TripOptionFragment : Fragment() {

    companion object {
        const val TRIP_OPTION_ARG = "tripOptionArg"
    }

    private val viewModel: TripOptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_trip_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tripOptionArg = arguments?.getParcelable<TripOptionArg>(TRIP_OPTION_ARG)
        Log.i("Teste", "onViewCreated: $tripOptionArg")
    }
}