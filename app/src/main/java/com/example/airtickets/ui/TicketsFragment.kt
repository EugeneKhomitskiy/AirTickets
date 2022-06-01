package com.example.airtickets.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.airtickets.R
import com.example.airtickets.adapter.OnInteractionListener
import com.example.airtickets.adapter.TicketAdapter
import com.example.airtickets.databinding.FragmentTicketsBinding
import com.example.airtickets.dto.Ticket
import com.example.airtickets.viewmodel.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketsFragment : Fragment() {

    private val ticketViewModel: TicketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentTicketsBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = TicketAdapter(object : OnInteractionListener {
            override fun onOpenTicket(ticket: Ticket) {
                val bundle = Bundle().apply {
                    putString("searchToken", ticket.searchToken)
                }
                findNavController().navigate(
                    R.id.action_ticketsFragment_to_singleTicketFragment,
                    bundle
                )
            }

            override fun onLike(ticket: Ticket) {
                when (ticket.likedByMe) {
                    true -> ticketViewModel.dislike(ticket.searchToken)
                    false -> ticketViewModel.like(ticket.searchToken)
                }
            }
        })

        binding.list.adapter = adapter

        ticketViewModel.data.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        ticketViewModel.dataState.observe(viewLifecycleOwner) { state ->
            when {
                state.error -> {
                    Toast.makeText(context, R.string.error_loading, Toast.LENGTH_SHORT).show()
                }
            }
            binding.progress.isVisible = state.loading
        }

        return binding.root
    }
}