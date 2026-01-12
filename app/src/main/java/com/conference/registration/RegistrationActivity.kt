package com.conference.registration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.conference.registration.databinding.ActivityRegistrationBinding
import com.conference.registration.viewmodel.RegistrationViewModel
import java.io.File

class RegistrationActivity : AppCompatActivity() {
    // kayıt ekranı binding
    private lateinit var binding: ActivityRegistrationBinding
    // kayıt viewmodel
    private lateinit var viewModel: RegistrationViewModel
    // son çekilen fotoğraf yolu
    private var currentPhotoPath: String? = null

    // kamera izin isteği
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Kamera izni gerekli", Toast.LENGTH_SHORT).show()
        }
    }

    // kamera sonucu
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoPath != null) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            binding.ivProfilePhoto.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        setupTitleSpinner()
        setupListeners()
        observeViewModel()
    }

    private fun setupTitleSpinner() {
        // ünvan spinner
        val titles = arrayOf("Prof.", "Dr.", "Student")
        val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, titles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTitle.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnConferenceInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(intent)
        }

        binding.btnTakePhoto.setOnClickListener {
            checkCameraPermission()
        }

        binding.btnRegister.setOnClickListener {
            registerParticipant()
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        try {
            val photoFile = createImageFile()
            currentPhotoPath = photoFile.absolutePath

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                photoFile
            )

            cameraLauncher.launch(photoURI)
        } catch (e: Exception) {
            Toast.makeText(this, "Kamera hatası", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        )
    }

    private fun registerParticipant() {
        val userIdText = binding.etUserId.text.toString().trim()
        val fullName = binding.etFullName.text.toString().trim()

        if (userIdText.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = try {
            userIdText.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Geçerli bir kullanıcı ID giriniz", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedTitle = binding.spinnerTitle.selectedItem.toString()
        val registrationType = when (binding.radioGroupType.checkedRadioButtonId) {
            R.id.radioFull -> 1
            R.id.radioStudent -> 2
            R.id.radioNone -> 3
            else -> 1
        }

        viewModel.registerParticipant(userId, fullName, selectedTitle, registrationType, currentPhotoPath)
    }

    private fun observeViewModel() {
        viewModel.registrationStatus.observe(this) { status ->
            when (status) {
                is RegistrationViewModel.RegistrationStatus.Success -> {
                    Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is RegistrationViewModel.RegistrationStatus.Error -> {
                    Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
