package com.gmail.devpelegrino.zhupper.ui.trip.option

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.devpelegrino.zhupper.databinding.ItemTripOptionBinding
import com.gmail.devpelegrino.zhupper.model.OptionModel
import com.gmail.devpelegrino.zhupper.ui.utils.OnClickChooseButton
import java.text.NumberFormat
import java.util.Locale

class TripOptionAdapter(
    private val options: List<OptionModel>,
    private val onClickChooseButton: OnClickChooseButton
) : RecyclerView.Adapter<TripOptionAdapter.TripOptionViewHolder>() {

    companion object {
        private const val TEMPLATE_REVIEW_TEXT = "%s/5 - %s"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripOptionViewHolder {
        val binding = ItemTripOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TripOptionViewHolder(
            binding,
            onClickChooseButton
        )
    }

    override fun onBindViewHolder(holder: TripOptionViewHolder, position: Int) {
        val trip = options[position]
        holder.bind(trip)
    }

    override fun getItemCount(): Int = options.size

    class TripOptionViewHolder(
        private val binding: ItemTripOptionBinding,
        private val onClickChooseButton: OnClickChooseButton
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(option: OptionModel) = binding.run {
            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

            textDriverName.text = option.name
            textDescription.text = option.description
            textVehicle.text = option.vehicle
            textPrice.text = currencyFormat.format(option.value)
            textReview.text = String.format(
                TEMPLATE_REVIEW_TEXT,
                option.review?.rating.toString(),
                option.review?.comment
            )

            buttonChoose.setOnClickListener {
                onClickChooseButton(option)
            }
        }
    }
}
