package com.example.airtickets.api

import com.example.airtickets.dto.Tickets
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("cheap")
    suspend fun getTickets(): Response<Tickets>
}