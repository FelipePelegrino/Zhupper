package com.gmail.devpelegrino.zhupper.ui.trip.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.devpelegrino.zhupper.R
import com.gmail.devpelegrino.zhupper.databinding.FragmentRequestTripBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RequestTripFragment : Fragment() {

    private var _binding: FragmentRequestTripBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RequestTripViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    private fun setUpListeners() = binding.includeFormRequestTrip.run {
        buttonRequest.setOnClickListener {
            viewModel.requestRideTest(
                customerId = textInputUserId.editText?.text.toString(),
                origin = textInputSourceAddress.editText?.text.toString(),
                destination = textInputDestinyAddress.editText?.text.toString()
            )
        }
    }
}
