package com.example.airtickets.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.airtickets.entity.TicketsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {

    @Query("SELECT * FROM TicketsEntity ORDER BY endCity ASC")
    fun get(): Flow<List<TicketsEntity>>

    @Insert
    suspend fun insert(tickets: List<TicketsEntity>)

    @Query(
        """
           UPDATE TicketsEntity SET likedByMe = 1
           WHERE searchToken = :searchToken AND likedByMe = 0;
        """
    )
    suspend fun like(searchToken: String)

    @Query(
        """
           UPDATE TicketsEntity SET likedByMe = 0
           WHERE searchToken = :searchToken AND likedByMe = 1;
        """
    )
    suspend fun dislike(searchToken: String)
}