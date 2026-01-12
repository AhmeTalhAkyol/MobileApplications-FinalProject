package com.conference.registration

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.conference.registration.data.Participant
import com.conference.registration.databinding.ActivityVerificationBinding
import com.conference.registration.viewmodel.VerificationViewModel
import java.io.File

class VerificationActivity : AppCompatActivity() {
    // doğrulama ekranı binding
    private lateinit var binding: ActivityVerificationBinding
    // doğrulama viewmodel
    private lateinit var viewModel: VerificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[VerificationViewModel::class.java]

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnVerify.setOnClickListener {
            val userIdText = binding.etSearchUserId.text.toString().trim()
            if (userIdText.isEmpty()) {
                Toast.makeText(this, "Lütfen kullanıcı ID giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = try {
                userIdText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Geçerli bir kullanıcı ID giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Yeni aramadan önce ekranı temizliyoruz
            binding.layoutParticipantInfo.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.mainLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))

            viewModel.verifyParticipant(userId)
        }
    }

    private fun observeViewModel() {
        viewModel.participant.observe(this) { participant ->
            if (participant != null) {
                showParticipantInfo(participant)
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            if (errorMessage != null) {
                showError(errorMessage)
            }
        }
    }

    private fun showParticipantInfo(participant: Participant) {
        binding.layoutParticipantInfo.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE

        binding.tvParticipantName.text = participant.fullName
        binding.tvParticipantTitle.text = participant.title
        binding.tvRegistrationType.text = "Kayıt Tipi: ${participant.registrationType}"

        if (participant.photoPath != null) {
            val photoFile = File(participant.photoPath)
            if (photoFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                binding.ivParticipantPhoto.setImageBitmap(bitmap)
            } else {
                binding.ivParticipantPhoto.setImageResource(android.R.drawable.ic_menu_camera)
            }
        } else {
            binding.ivParticipantPhoto.setImageResource(android.R.drawable.ic_menu_camera)
        }

        val backgroundColor = when (participant.registrationType) {
            1 -> ContextCompat.getColor(this, R.color.green)
            2 -> ContextCompat.getColor(this, R.color.blue)
            3 -> ContextCompat.getColor(this, R.color.orange)
            else -> ContextCompat.getColor(this, R.color.green)
        }

        binding.mainLayout.setBackgroundColor(backgroundColor)
    }

    private fun showError(errorMessage: String) {
        binding.layoutParticipantInfo.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = errorMessage

        val redColor = ContextCompat.getColor(this, R.color.red)
        binding.mainLayout.setBackgroundColor(redColor)
    }
}
