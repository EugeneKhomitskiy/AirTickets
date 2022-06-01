package com.example.airtickets.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.airtickets.dto.Ticket

@Entity
data class TicketsEntity(
    @PrimaryKey(autoGenerate = false)
    val searchToken: String,
    val startCity: String,
    val startCityCode: String,
    val endCity: String,
    val endCityCode: String,
    val startDate: String,
    val endDate: String,
    val price: Int,
    val likedByMe: Boolean
) {
    fun toDto() = Ticket(
        searchToken,
        startCity,
        startCityCode,
        endCity,
        endCityCode,
        startDate,
        endDate,
        price,
        likedByMe
    )

    companion object {
        fun fromDto(dto: Ticket) = TicketsEntity(
            dto.searchToken,
            dto.startCity,
            dto.startCityCode,
            dto.endCity,
            dto.endCityCode,
            dto.startDate,
            dto.endDate,
            dto.price,
            dto.likedByMe
        )
    }
}

fun List<TicketsEntity>.toDto() = map(TicketsEntity::toDto)
fun List<Ticket>.toEntity() = map(TicketsEntity::fromDto)