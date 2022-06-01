package com.example.airtickets.repository

import com.example.airtickets.api.ApiService
import com.example.airtickets.dao.TicketDao
import com.example.airtickets.entity.TicketsEntity
import com.example.airtickets.entity.toDto
import com.example.airtickets.entity.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao,
    private val apiService: ApiService
) {

    val data = ticketDao.get()
        .map(List<TicketsEntity>::toDto)
        .flowOn(Dispatchers.Default)

    suspend fun getTickets() {
        try {
            ticketDao.get()
            val response = apiService.getTickets()
            val body = response.body()?.data
            if (body != null) {
                ticketDao.insert(body.toEntity())
            }
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun like(searchToken: String) {
        try {
            ticketDao.like(searchToken)
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    suspend fun dislike(searchToken: String) {
        try {
            ticketDao.dislike(searchToken)
        } catch (e: Exception) {
            throw UnknownError()
        }
    }
}