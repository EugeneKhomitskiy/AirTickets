package com.example.airtickets.viewmodel

import androidx.lifecycle.*
import com.example.airtickets.dto.Ticket
import com.example.airtickets.model.TicketModelState
import com.example.airtickets.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    val data: LiveData<List<Ticket>> = ticketRepository.data.asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<TicketModelState>()
    val dataState: LiveData<TicketModelState>
        get() = _dataState

    init {
        loadTickets()
    }

    private fun loadTickets() = viewModelScope.launch {
        _dataState.value = TicketModelState(loading = true)
        try {
            ticketRepository.getTickets()
            _dataState.value = TicketModelState()
        } catch (e: Exception) {
            _dataState.value = TicketModelState(error = true)
        }
    }

    fun like(searchToken: String) = viewModelScope.launch {
        try {
            ticketRepository.like(searchToken)
        } catch (e: Exception) {
            _dataState.value = TicketModelState(error = true)
        }
    }

    fun dislike(searchToken: String) = viewModelScope.launch {
        try {
            ticketRepository.dislike(searchToken)
        } catch (e: Exception) {
            _dataState.value = TicketModelState(error = true)
        }
    }
}