package com.gmail.devpelegrino.zhupper.ui.trip.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gmail.devpelegrino.zhupper.R
import com.gmail.devpelegrino.zhupper.databinding.FragmentRequestTripBinding
import com.gmail.devpelegrino.zhupper.ui.trip.option.TripOptionArg
import com.gmail.devpelegrino.zhupper.ui.trip.option.TripOptionFragment.Companion.TRIP_OPTION_ARG
import com.gmail.devpelegrino.zhupper.ui.utils.setGoneAnimated
import com.gmail.devpelegrino.zhupper.ui.utils.setSafeOnClickListener
import com.gmail.devpelegrino.zhupper.ui.utils.setVisibleAnimated
import com.gmail.devpelegrino.zhupper.ui.utils.showErrorDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RequestTripFragment : Fragment() {

    private var _binding: FragmentRequestTripBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RequestTripViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.cleanUiState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
        getSavedTextState()
    }

    private fun setUpListeners() = binding.includeFormRequestTrip.run {
        buttonRequest.setSafeOnClickListener {
            requestRide()
        }
        textInputUserId.editText?.addTextChangedListener { text ->
            viewModel.updateUserIdTextState(text.toString())
        }
        textInputSourceAddress.editText?.addTextChangedListener { text ->
            viewModel.updateSourceAddressTextState(text.toString())
        }
        textInputDestinationAddress.editText?.addTextChangedListener { text ->
            viewModel.updateDestinationAddressTextState(text.toString())
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is RequestTripViewModel.RequestTripUiState.Success -> {
                            val tripOptionArg = TripOptionArg(
                                userId = viewModel.userIdTextState,
                                sourceAddress = viewModel.sourceAddressTextState,
                                destinationAddress = viewModel.destinationAddressTextState,
                                sourceLocation = uiState.estimateRideModel.origin,
                                destinationLocation = uiState.estimateRideModel.destination,
                                options = uiState.estimateRideModel.options
                            )

                            val bundle = Bundle().apply {
                                putParcelable(TRIP_OPTION_ARG, tripOptionArg)
                            }

                            findNavController().navigate(
                                R.id.action_fragment_request_trip_to_fragment_trip_option,
                                bundle
                            )
                        }

                        is RequestTripViewModel.RequestTripUiState.ApiError -> {
                            showErrorDialog(
                                fragmentContext = requireContext(),
                                errorMessage = uiState.errorDescription,
                                onRetry = { requestRide() }
                            )
                        }

                        RequestTripViewModel.RequestTripUiState.Loading -> {
                            binding.includeLoading.root.setVisibleAnimated()
                        }

                        RequestTripViewModel.RequestTripUiState.Loaded -> {
                            binding.includeLoading.root.setGoneAnimated()
                        }

                        RequestTripViewModel.RequestTripUiState.NetworkError -> {
                            showErrorDialog(
                                fragmentContext = requireContext(),
                                errorMessage = resources.getString(
                                    R.string.network_error_description
                                ),
                                onRetry = { requestRide() }
                            )
                        }

                        RequestTripViewModel.RequestTripUiState.UnexpectedError -> {
                            showErrorDialog(
                                fragmentContext = requireContext(),
                                errorMessage = resources.getString(
                                    R.string.unexpected_error_description
                                ),
                                onRetry = { requestRide() }
                            )
                        }

                        RequestTripViewModel.RequestTripUiState.Idle -> {}
                    }
                }
            }
        }
    }

    private fun requestRide() = binding.includeFormRequestTrip.run {
        viewModel.requestRideTest(
            customerId = textInputUserId.editText?.text.toString(),
            origin = textInputSourceAddress.editText?.text.toString(),
            destination = textInputDestinationAddress.editText?.text.toString()
        )
    }

    private fun getSavedTextState() = binding.includeFormRequestTrip.run {
        textInputUserId.editText?.setText(viewModel.userIdTextState)
        textInputSourceAddress.editText?.setText(viewModel.sourceAddressTextState)
        textInputDestinationAddress.editText?.setText(viewModel.destinationAddressTextState)
    }
}
