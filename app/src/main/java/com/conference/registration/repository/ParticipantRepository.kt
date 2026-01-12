package com.conference.registration.repository

import com.conference.registration.data.Participant
import com.conference.registration.data.ParticipantDao

// participant repository
class ParticipantRepository(private val participantDao: ParticipantDao) {
    suspend fun insertParticipant(participant: Participant) {
        participantDao.insertParticipant(participant)
    }

    suspend fun getParticipantById(userId: Int): Participant? {
        return participantDao.getParticipantById(userId)
    }

    suspend fun userIdExists(userId: Int): Boolean {
        return participantDao.userIdExists(userId)
    }
}
