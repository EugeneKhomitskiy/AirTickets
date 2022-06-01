package com.example.airtickets.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.airtickets.databinding.CardTicketBinding
import com.example.airtickets.dto.Ticket
import com.example.airtickets.extension.anim
import com.example.airtickets.extension.toDate

interface OnInteractionListener {
    fun onOpenTicket(ticket: Ticket)
    fun onLike(ticket: Ticket)
}

class TicketAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<Ticket, TicketViewHolder>(TicketDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = CardTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = getItem(position)
        holder.bind(ticket)
    }
}

class TicketViewHolder(
    private val binding: CardTicketBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(ticket: Ticket) {
        with(binding) {
            startCity.text = ticket.startCity
            startCityCode.text = ticket.startCityCode
            endCity.text = ticket.endCity
            endCityCode.text = ticket.endCityCode
            date.text = "${ticket.startDate.toDate().substring(0, 12)}  ${
                ticket.endDate.toDate().substring(0, 12)
            }"
            price.text = "${ticket.price} ₽"
            like.isChecked = ticket.likedByMe

            cardView.setOnClickListener {
                onInteractionListener.onOpenTicket(ticket)
            }

            binding.like.setOnClickListener { view ->
                onInteractionListener.onLike(ticket)
                view.anim(view)
            }
        }
    }
}

class TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {
    override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
        return oldItem.searchToken == newItem.searchToken
    }

    override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Ticket, newItem: Ticket): Any =
        Payload(
            likedByMe = newItem.likedByMe.takeIf { it != oldItem.likedByMe }
        )
}

data class Payload(
    val likedByMe: Boolean? = null
)