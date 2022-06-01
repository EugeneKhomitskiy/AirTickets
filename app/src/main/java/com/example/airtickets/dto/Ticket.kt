package com.example.airtickets.dto

data class Ticket(
    val searchToken: String,
    val startCity: String,
    val startCityCode: String,
    val endCity: String,
    val endCityCode: String,
    val startDate: String,
    val endDate: String,
    val price: Int,
    val likedByMe: Boolean
)

data class Tickets(
    val data: List<Ticket>
)