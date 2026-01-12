# Conference Registration and Verification System

This project was developed as part of the Mobile Applications course
(CENG 443) final project at Ankara Science University.

## About the Project

An Android-based conference participant registration and verification system.
The application allows new users to register and verifies registered users.

## Features

### Module A: Participant Registration
- Input fields for User ID, Full Name, Title, and Registration Type
- Profile photo capture (camera integration)
- Conference info button (redirects to website)
- Data saved to Room Database

### Module B: Participant Verification
- Search by User ID
- Display found user information
- Dynamic background color based on registration type:
  - Type 1 (Full): Green
  - Type 2 (Student): Blue
  - Type 3 (None): Orange
- Red background and error message if user not found

## Technical Specifications

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Database:** Room Persistence Library (SQLite)
- **Camera:** Android Camera Intent
- **UI:** Material Design Components
- **View Binding:** Enabled

## Requirements

- Android Studio Hedgehog or higher
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Gradle 8.0+

## Installation and Running

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Connect an Android device or emulator
4. Run the application (Run 'app')

### Important Notes

- The app will request camera permission on first launch
- Photos are stored in the device's external storage
- Database is created in the application data folder

## Project Structure

```
app/
├── src/main/
│   ├── java/com/conference/registration/
│   │   ├── data/
│   │   │   ├── Participant.kt          # Room Entity
│   │   │   ├── ParticipantDao.kt       # Data Access Object
│   │   │   └── ConferenceDatabase.kt   # Room Database
│   │   ├── repository/
│   │   │   └── ParticipantRepository.kt
│   │   ├── viewmodel/
│   │   │   ├── RegistrationViewModel.kt
│   │   │   └── VerificationViewModel.kt
│   │   ├── MainActivity.kt
│   │   ├── RegistrationActivity.kt
│   │   └── VerificationActivity.kt
│   ├── res/
│   │   ├── layout/                      # XML layout files
│   │   ├── values/                      # String, color, theme
│   │   └── xml/
│   │       └── file_paths.xml          # FileProvider configuration
│   └── AndroidManifest.xml
```

## Usage

### Registration Process
1. Click the "Registration" button from the main screen
2. Fill in all fields (User ID, Full Name, Title, Registration Type)
3. Click "Take Photo" to capture a profile photo
4. Click "Register" button

### Verification Process
1. Click the "Verification" button from the main screen
2. Enter the registered user ID
3. Click "Verify" button
4. Results will be displayed on screen

## Error Handling

The application handles the following scenarios:
- User is informed if camera permission is denied
- Empty field validation is performed
- Warning is shown if attempting to register with an existing User ID
- Error message is displayed if user is not found
- Database operation errors are caught with try-catch

## Developer Notes

- Room Database is used
- MVVM architecture is implemented
- Coroutines are used
- Camera is managed with FileProvider

## License

This project is developed for educational purposes.
