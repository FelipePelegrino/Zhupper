package com.gmail.devpelegrino.zhupper.ui.trip.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.devpelegrino.zhupper.databinding.ItemRideHistoryBinding
import com.gmail.devpelegrino.zhupper.model.RideModel
import com.gmail.devpelegrino.zhupper.ui.utils.toCurrencyLocalNumberFormat
import com.gmail.devpelegrino.zhupper.ui.utils.toFriendlyDate
import java.text.DecimalFormat

class RideHistoryAdapter : RecyclerView.Adapter<RideHistoryAdapter.RideHistoryViewHolder>() {

    private var rides: List<RideModel?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHistoryViewHolder {
        val binding = ItemRideHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RideHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RideHistoryViewHolder, position: Int) {
        val ride = rides[position]
        ride?.let {
            holder.bind(ride)
        }
    }

    override fun getItemCount(): Int = rides.size

    fun clearAndUpdateNewData(rides: List<RideModel?>) {
        this.rides = rides
        notifyDataSetChanged()
    }

    class RideHistoryViewHolder(
        private val binding: ItemRideHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ride: RideModel) = binding.run {
            textCalendar.text = ride.date.toFriendlyDate()
            textDriverName.text = ride.driver.name
            textOrigin.text = ride.origin
            textDestination.text = ride.destination
            textDistance.text = DecimalFormat("#.##").format(ride.distance)
            textTime.text = ride.duration
            textPrice.text = ride.value.toCurrencyLocalNumberFormat()
        }
    }
}
