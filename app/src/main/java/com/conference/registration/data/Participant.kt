package com.conference.registration.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// participant tablosu
@Entity(tableName = "participants")
data class Participant(
    @PrimaryKey
    val userId: Int,           // Kullanıcı ID (benzersiz)
    val fullName: String,      // Ad Soyad
    val title: String,         // Ünvan (Prof., Dr., Student)
    val registrationType: Int, // 1-Full, 2-Student, 3-None
    val photoPath: String?     // Çekilen fotoğrafın dosya yolu
)
