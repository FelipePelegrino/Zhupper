package com.gmail.devpelegrino.zhupper.ui.trip.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.devpelegrino.zhupper.R
import com.gmail.devpelegrino.zhupper.databinding.FragmentTripHistoryBinding
import com.gmail.devpelegrino.zhupper.ui.trip.history.TripHistoryViewModel.Companion.SPINNER_ALL_DRIVERS
import com.gmail.devpelegrino.zhupper.ui.trip.history.TripHistoryViewModel.Companion.SPINNER_DRIVER_ID_ONE
import com.gmail.devpelegrino.zhupper.ui.trip.history.TripHistoryViewModel.Companion.SPINNER_DRIVER_ID_THREE
import com.gmail.devpelegrino.zhupper.ui.trip.history.TripHistoryViewModel.Companion.SPINNER_DRIVER_ID_TWO
import com.gmail.devpelegrino.zhupper.ui.trip.history.TripHistoryViewModel.TripHistoryUiState
import com.gmail.devpelegrino.zhupper.ui.utils.setGoneAnimated
import com.gmail.devpelegrino.zhupper.ui.utils.setSafeOnClickListener
import com.gmail.devpelegrino.zhupper.ui.utils.setVisibleAnimated
import com.gmail.devpelegrino.zhupper.ui.utils.showErrorDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripHistoryFragment : Fragment() {
    private var _binding: FragmentTripHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TripHistoryViewModel by viewModel()
    private var lastSpinnerPosition = 0
    private lateinit var mAdapter: RideHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripHistoryBinding.inflate(inflater, container, false)
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

        setUpRecyclerView()
        setUpDriverSpinner()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpRecyclerView() {
        mAdapter = RideHistoryAdapter()
        binding.recyclerRides.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRides.adapter = mAdapter
    }

    private fun setUpDriverSpinner() = binding.includeFormHistoryTrip.run {
        val driverSpinnerData = viewModel.getDriverData()
        val populateSpinner = driverSpinnerData.map {
            when (it) {
                SPINNER_ALL_DRIVERS -> {
                    resources.getString(R.string.spinner_all_drivers)
                }

                SPINNER_DRIVER_ID_ONE -> {
                    resources.getString(R.string.spinner_mock_driver_one)
                }

                SPINNER_DRIVER_ID_TWO -> {
                    resources.getString(R.string.spinner_mock_driver_two)
                }

                SPINNER_DRIVER_ID_THREE -> {
                    resources.getString(R.string.spinner_mock_driver_three)
                }

                else -> {
                    ""
                }
            }
        }
        val driverAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            populateSpinner
        )
        materialAutoCompleteDriverSelector.setAdapter(driverAdapter)
        materialAutoCompleteDriverSelector.setOnItemClickListener { _, _, position, _ ->
            lastSpinnerPosition = position
        }
    }

    private fun setUpListeners() = binding.includeFormHistoryTrip.run {
        buttonApply.setSafeOnClickListener {
            viewModel.getRideHistory(
                customerId = textInputUserId.editText?.text.toString(),
                spinnerPosition = lastSpinnerPosition
            )
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is TripHistoryUiState.Success -> {
                            if (uiState.rides.isNotEmpty()) {
                                binding.recyclerRides.isVisible = true
                                mAdapter.clearAndUpdateNewData(uiState.rides)
                            } else {
                                //TODO: aqui
                                binding.recyclerRides.isVisible = false
                            }
                        }

                        is TripHistoryUiState.ApiError -> {
                            binding.recyclerRides.isVisible = false
                            showErrorDialog(
                                fragmentContext = requireContext(),
                                errorMessage = uiState.errorDescription
                            )
                        }

                        TripHistoryUiState.NetworkError -> {
                            binding.recyclerRides.isVisible = false
                            showErrorDialog(
                                fragmentContext = requireContext(),
                                errorMessage = resources.getString(
                                    R.string.network_error_description
                                )
                            )
                        }

                        TripHistoryUiState.UnexpectedError -> {
                            binding.recyclerRides.isVisible = false
                            showErrorDialog(
                                fragmentContext = requireContext(),
                                errorMessage = resources.getString(
                                    R.string.unexpected_error_description
                                )
                            )
                        }

                        TripHistoryUiState.Loading -> {
                            binding.includeLoading.root.setVisibleAnimated()
                        }

                        TripHistoryUiState.Loaded -> {
                            binding.includeLoading.root.setGoneAnimated()
                        }

                        TripHistoryUiState.Idle -> {}
                    }
                }
            }
        }
    }
}