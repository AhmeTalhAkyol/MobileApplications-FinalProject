package com.conference.registration.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParticipantDao {
    // insert veya güncelle
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: Participant)

    @Query("SELECT * FROM participants WHERE userId = :userId")
    suspend fun getParticipantById(userId: Int): Participant?

    @Query("SELECT EXISTS(SELECT 1 FROM participants WHERE userId = :userId)")
    suspend fun userIdExists(userId: Int): Boolean
}
