# Konferans Kayıt ve Doğrulama Sistemi

Bu proje, Ankara Bilim Üniversitesi Mobil Uygulamalar dersi (CENG 443) final projesi kapsamında geliştirilmiştir.

## Proje Hakkında

Android tabanlı bir konferans katılımcı kayıt ve doğrulama sistemidir. Uygulama, yeni kullanıcıların kayıt olmasını ve kayıtlı kullanıcıların doğrulanmasını sağlar.

## Özellikler

### Modül A: Katılımcı Kayıt (Registration)
- Kullanıcı ID, Ad Soyad, Ünvan ve Kayıt Tipi bilgilerinin girilmesi
- Profil fotoğrafı çekme (kamera entegrasyonu)
- Konferans bilgisi butonu (web sitesine yönlendirme)
- Verilerin Room Database'e kaydedilmesi

### Modül B: Katılımcı Doğrulama (Verification)
- Kullanıcı ID ile arama yapma
- Bulunan kullanıcının bilgilerini görüntüleme
- Kayıt tipine göre dinamik arka plan rengi:
  - Tip 1 (Full): Yeşil
  - Tip 2 (Student): Mavi
  - Tip 3 (None): Turuncu
- Kullanıcı bulunamazsa kırmızı arka plan ve hata mesajı

## Teknik Özellikler

- **Dil:** Kotlin
- **Mimari:** MVVM (Model-View-ViewModel)
- **Veritabanı:** Room Persistence Library (SQLite)
- **Kamera:** Android Camera Intent
- **UI:** Material Design Components
- **View Binding:** Aktif

## Gereksinimler

- Android Studio Hedgehog veya üzeri
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Gradle 8.0+

## Kurulum ve Çalıştırma

1. Projeyi Android Studio'da açın
2. Gradle sync işleminin tamamlanmasını bekleyin
3. Bir Android cihaz veya emülatör bağlayın
4. Uygulamayı çalıştırın (Run 'app')

### Önemli Notlar

- Uygulama ilk çalıştırıldığında kamera izni isteyecektir
- Fotoğraflar cihazın external storage'ında saklanır
- Veritabanı uygulama veri klasöründe oluşturulur

## Proje Yapısı

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
│   │   ├── layout/                      # XML layout dosyaları
│   │   ├── values/                      # String, color, theme
│   │   └── xml/
│   │       └── file_paths.xml          # FileProvider yapılandırması
│   └── AndroidManifest.xml
```

## Kullanım

### Kayıt İşlemi
1. Ana ekrandan "Kayıt" butonuna tıklayın
2. Tüm alanları doldurun (User ID, Ad Soyad, Ünvan, Kayıt Tipi)
3. "Fotoğraf Çek" butonuna tıklayarak profil fotoğrafı çekin
4. "Kaydet" butonuna tıklayın

### Doğrulama İşlemi
1. Ana ekrandan "Doğrulama" butonuna tıklayın
2. Kayıtlı kullanıcı ID'sini girin
3. "Doğrula" butonuna tıklayın
4. Sonuç ekranda görüntülenecektir

## Hata Yönetimi

Uygulama aşağıdaki durumları ele alır:
- Kamera izni reddedilirse kullanıcıya bilgi verilir
- Boş alan kontrolü yapılır
- Aynı User ID ile tekrar kayıt yapılmaya çalışılırsa uyarı verilir
- Kullanıcı bulunamazsa hata mesajı gösterilir
- Veritabanı işlemlerinde hata oluşursa try-catch ile yakalanır

## Geliştirici Notları

- Room Database kullanıldı
- MVVM mimarisi uygulandı
- Coroutines kullanıldı
- FileProvider ile kamera yönetildi

## Lisans

Bu proje eğitim amaçlı geliştirilmiştir.
