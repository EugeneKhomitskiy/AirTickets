package com.example.airtickets.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.airtickets.databinding.FragmentSingleTicketBinding
import com.example.airtickets.extension.anim
import com.example.airtickets.extension.toDate
import com.example.airtickets.viewmodel.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleTicketFragment : Fragment() {

    private val ticketViewModel: TicketViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSingleTicketBinding.inflate(
            inflater,
            container,
            false
        )

        val searchToken = arguments?.getString("searchToken")

        ticketViewModel.data.observe(viewLifecycleOwner) { list ->
            list.map { ticket ->
                if (ticket.searchToken == searchToken) {
                    with(binding) {
                        departRoute.text = "${ticket.startCity} - ${ticket.endCity}"
                        departDate.text = ticket.startDate.toDate()
                        backRoute.text = "${ticket.endCity} - ${ticket.startCity}"
                        returnDate.text = ticket.endDate.toDate()
                        price.text = "${ticket.price} ₽"
                        like.isChecked = ticket.likedByMe

                        like.setOnClickListener { view ->
                            when (ticket.likedByMe) {
                                true -> ticketViewModel.dislike(ticket.searchToken)
                                false -> ticketViewModel.like(ticket.searchToken)
                            }
                            view.anim(view)
                        }
                    }
                }
            }
        }

        return binding.root
    }
}