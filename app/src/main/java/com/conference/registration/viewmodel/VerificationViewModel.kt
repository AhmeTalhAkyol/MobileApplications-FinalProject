package com.conference.registration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.conference.registration.data.ConferenceDatabase
import com.conference.registration.data.Participant
import com.conference.registration.repository.ParticipantRepository
import kotlinx.coroutines.launch

class VerificationViewModel(application: Application) : AndroidViewModel(application) {
    // repository referansı
    private val repository: ParticipantRepository

    // bulunan katılımcı
    private val _participant = MutableLiveData<Participant?>()
    val participant: LiveData<Participant?> = _participant

    // hata mesajı
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        val database = ConferenceDatabase.getDatabase(application)
        repository = ParticipantRepository(database.participantDao())
    }

    fun verifyParticipant(userId: Int) {
        viewModelScope.launch {
            try {
                val foundParticipant = repository.getParticipantById(userId)
                if (foundParticipant != null) {
                    _participant.value = foundParticipant
                    _error.value = null
                } else {
                    _participant.value = null
                    _error.value = "Kullanıcı bulunamadı"
                }
            } catch (e: Exception) {
                _participant.value = null
                _error.value = "Hata: ${e.message}"
            }
        }
    }
}
