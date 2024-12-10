package com.gmail.devpelegrino.zhupper.ui.trip.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gmail.devpelegrino.zhupper.BuildConfig
import com.gmail.devpelegrino.zhupper.R
import com.gmail.devpelegrino.zhupper.databinding.FragmentTripOptionBinding
import com.gmail.devpelegrino.zhupper.model.LocationModel

class TripOptionFragment : Fragment() {

    companion object {
        const val TRIP_OPTION_ARG = "tripOptionArg"
        private const val STATIC_MAP_MARKER_WITH_LABEL_ORIGIN = "color:gray%7Clabel:O%7C"
        private const val STATIC_MAP_MARKER_WITH_LABEL_DESTINATION = "color:red%7Clabel:D%7C"
        private const val BASE_API_STATIC_MAP_URL =
            "https://maps.googleapis.com/maps/api/staticmap?"
        private const val STATIC_MAP_SIZE = "800x800"
    }

    private var _binding: FragmentTripOptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TripOptionViewModel by viewModels()
    private val tripOptionArg: TripOptionArg? by lazy {
        arguments?.getParcelable(TRIP_OPTION_ARG)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setObservers()
    }

    private fun setUpView() {
        tripOptionArg?.let { tripOption ->
            bindMap(
                tripOption.sourceLocation,
                tripOption.destinationLocation
            )

            tripOption.options?.let {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = TripOptionAdapter(
                    tripOption.options
                )
            }
        }
    }

    private fun setObservers() {

    }

    private fun bindMap(
        sourceLocationModel: LocationModel?,
        destinationLocationModel: LocationModel?
    ) {
        val url = buildStaticMapUrl(sourceLocationModel, destinationLocationModel)

        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_downloading)
            .error(R.drawable.ic_error)
            .into(binding.mapImageView)
    }

    private fun buildStaticMapUrl(
        sourceLocationModel: LocationModel?,
        destinationLocationModel: LocationModel?
    ): String {
        val apiKey = BuildConfig.API_KEY_MAPS_STATIC
        val markerA = sourceLocationModel?.let {
            "$STATIC_MAP_MARKER_WITH_LABEL_ORIGIN${it.latitude},${it.longitude}"
        }
        val markerB = destinationLocationModel?.let {
            "$STATIC_MAP_MARKER_WITH_LABEL_DESTINATION${it.latitude},${it.longitude}"
        }
        val pathA = sourceLocationModel?.let {
            "${it.latitude},${it.longitude}"
        }
        val pathB = destinationLocationModel?.let {
            "${it.latitude},${it.longitude}"
        }

        return BASE_API_STATIC_MAP_URL +
                "markers=$markerA&markers=$markerB" +
                "&path=$pathA|$pathB" +
                "&size=$STATIC_MAP_SIZE" +
                "&key=$apiKey"
    }
}