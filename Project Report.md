This file is a brief explanation document for the `Conference Registration` Android project.

## Project Purpose

The purpose of the application is to store registration information of conference
participants on the phone and verify them by ID at entry.
There are two main screens: registration screen and verification screen.
All data is stored in the Room database on the device, no internet connection is required.

## Screens and Flow

- **Main Screen:** Just two buttons: `Registration` and `Verification`. 
  - User selects which operation they want to perform.
- **Registration Screen (Module A):**
  - Fields for User ID (int), Full Name, Title (Spinner: Prof., Dr., Student) 
    and Registration Type (RadioButton: 1-Full, 2-Student, 3-None).
  - A simple button opens the conference website
    (currently set to Google as default, can be changed to actual link for project submission).
  - `Take Photo` button calls camera intent, captured photo is saved
     to app's own folder and path is written to database.
  - When `Register` button is pressed, fields are validated first,
    then Room is checked if the same ID was used before.
    If exists, shows warning; otherwise adds new record.
- **Verification Screen (Module B):**
  - Only ID is requested from user.
  - When `Verify` button is pressed, record is searched in Room.
  - If record is found, photo, name, title and registration type are displayed.
  - Background color is green for type 1, blue for type 2, orange for type 3.
  - If record is not found, red background and appropriate error message are shown.

## Technical Choices

- **Language:** Kotlin
- **Architecture:** MVVM (Activity + ViewModel + Repository + Room)
- **Database:** Room (single table: `Participant`)
  - Columns: `userId` (PrimaryKey), `fullName`, `title`, `registrationType`, `photoPath`
- **UI:** XML layout + ViewBinding, Material components (TextInputLayout, Button, etc.)
- **Camera:** Simple intent structure with `ActivityResultContracts.TakePicture` and `FileProvider`

Database operations in ViewModel are done in background with `viewModelScope.launch`.
This prevents UI freezing. Repository layer is not very complex; I used it just to collect DAO calls.

## Error and Edge Cases

- If ID or name is empty, registration is not performed, user is warned with Toast.
- If ID is not integer, user is warned again.
- If attempting to register with same ID second time, there's a check before adding to database.
- If camera permission is not granted, a short message is shown on registration screen.
- If photo path exists in database but file is deleted, default camera icon is shown on verification screen.

## Github Repo
- https://github.com/AhmeTalhAkyol/MobileApplications-FinalProject


