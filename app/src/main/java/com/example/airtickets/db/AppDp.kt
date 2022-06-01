package com.example.airtickets.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.airtickets.dao.TicketDao
import com.example.airtickets.entity.TicketsEntity

@Database(
    entities = [TicketsEntity::class],
    version = 1
)

abstract class AppDb : RoomDatabase() {
    abstract val ticketDao: TicketDao
}