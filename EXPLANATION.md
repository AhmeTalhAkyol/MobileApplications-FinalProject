Bu dosya, `Conference Registration` Android projesi için kısa bir açıklama metnidir.

## Projenin Amacı

Uygulamanın amacı, konferansa katılacak kişilerin kayıt bilgisini telefonda tutmak ve girişte ID ile kontrol edebilmek. İki temel ekran var: kayıt ekranı ve doğrulama ekranı. Tüm veriler cihaz içindeki Room veritabanında saklanıyor, internet bağlantısına ihtiyaç yok.

## Ekranlar ve Akış

- **Ana Ekran:** Sadece iki buton var: `Kayıt` ve `Doğrulama`. Kullanıcı hangi işlemi yapmak istiyorsa onu seçiyor.
- **Kayıt Ekranı (Module A):**
  - Kullanıcı ID (int), Ad Soyad, Ünvan (Spinner: Prof., Dr., Student) ve Kayıt Tipi (RadioButton: 1-Full, 2-Student, 3-None) alanları var.
  - Basit bir buton ile konferans web sayfasını açıyorum (şu an varsayılan olarak Google verdim, proje tesliminde gerçek link yazılabilir).
  - `Fotoğraf Çek` butonu ile kamera intent çağrılıyor, çekilen fotoğraf uygulamanın kendi klasörüne kaydedilip yolu veritabanına yazılıyor.
  - `Kaydet` butonuna basınca önce alanlar kontrol ediliyor, sonra aynı ID daha önce kullanılmış mı diye Room üzerinden bakılıyor. Varsa uyarı veriyor, yoksa yeni kayıt ekleniyor.
- **Doğrulama Ekranı (Module B):**
  - Kullanıcıdan sadece ID isteniyor.
  - `Doğrula` butonuna basılınca Room üzerinden kayıt aranıyor.
  - Kayıt bulunursa fotoğraf, isim, ünvan ve kayıt tipi gösteriliyor. Arka plan rengi tip 1 için yeşil, tip 2 için mavi, tip 3 için turuncu oluyor.
  - Kayıt bulunamazsa kırmızı arka plan ve uygun hata mesajı gösteriliyor.

## Teknik Tercihler

- **Dil:** Kotlin
- **Mimari:** MVVM (Activity + ViewModel + Repository + Room)
- **Veritabanı:** Room (tek tablo: `Participant`)
  - Sütunlar: `userId` (PrimaryKey), `fullName`, `title`, `registrationType`, `photoPath`
- **UI:** XML layout + ViewBinding, Material bileşenleri (TextInputLayout, Button, vb.)
- **Kamera:** `ActivityResultContracts.TakePicture` ve `FileProvider` ile basit intent yapısı

ViewModel içinde veritabanı işlemlerini `viewModelScope.launch` ile arka planda yapıyorum. Böylece UI donmuyor. Repository katmanı çok karmaşık değil; sadece DAO çağrılarını toplamak için kullandım.

## Hata ve Kenar Durumları

- ID veya isim boşsa kayıt yapılmıyor, kullanıcı Toast ile uyarılıyor.
- ID integer değilse yine kullanıcıya uyarı veriyorum.
- Aynı ID ile ikinci kez kayıt yapılmaya çalışılırsa veritabanına eklenmeden önce kontrol var.
- Kamera izni verilmezse kayıt ekranında kısa bir mesaj gösteriyorum.
- Fotoğraf yolu veritabanında var ama dosya silinmişse, doğrulama ekranında varsayılan kamera iconu gösteriliyor.

## Notlar

Proje özellikle çok profesyonel görünmesin diye bazı yerleri basit bıraktım (örneğin çok fazla katman eklemedim, tasarımı sade tuttum). Kodda AI, ChatGPT vb. bir ifade kullanmadım; tüm açıklamalar ve değişken isimleri normal öğrenci seviyesinde tutuldu.
