package com.conference.registration.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// app Room database
@Database(entities = [Participant::class], version = 1, exportSchema = false)
abstract class ConferenceDatabase : RoomDatabase() {
    abstract fun participantDao(): ParticipantDao

    companion object {
        @Volatile
        private var INSTANCE: ConferenceDatabase? = null

        fun getDatabase(context: Context): ConferenceDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ConferenceDatabase::class.java,
                            "conference_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
