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

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    // kayıt repository
    private val repository: ParticipantRepository

    // kayıt sonucu durumu
    private val _registrationStatus = MutableLiveData<RegistrationStatus>()
    val registrationStatus: LiveData<RegistrationStatus> = _registrationStatus

    init {
        val database = ConferenceDatabase.getDatabase(application)
        repository = ParticipantRepository(database.participantDao())
    }

    fun registerParticipant(
        userId: Int,
        fullName: String,
        title: String,
        registrationType: Int,
        photoPath: String?
    ) {
        viewModelScope.launch {
            try {
                if (repository.userIdExists(userId)) {
                    _registrationStatus.value = RegistrationStatus.Error("Bu kullanıcı ID zaten kayıtlı")
                    return@launch
                }

                val participant = Participant(
                    userId,
                    fullName,
                    title,
                    registrationType,
                    photoPath
                )

                repository.insertParticipant(participant)
                _registrationStatus.value = RegistrationStatus.Success("Kayıt başarılı")
            } catch (e: Exception) {
                _registrationStatus.value = RegistrationStatus.Error("Hata: ${e.message}")
            }
        }
    }

    sealed class RegistrationStatus {
        data class Success(val message: String) : RegistrationStatus()
        data class Error(val message: String) : RegistrationStatus()
    }
}
