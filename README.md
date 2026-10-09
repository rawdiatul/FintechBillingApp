# FintechBillingApp - Digital Invoice Parser
> Tugas Praktikum Pemrograman Mobile Dasar: Pemodelan Data Berstruktur, Kotlin Data Class DTO, dan Parsing JSON dengan Google Gson.

---

## 1. Identitas Project
* **Nama Project:** `FintechBillingApp`
* **Package / Application ID:** `com.industri.fintechbilling`
* **Bahasa:** Kotlin
* **UI Toolkit:** Android XML Views & ViewBinding (Tanpa Jetpack Compose)
* **Minimum SDK:** API 24 (Android 7.0 Nougat)
* **Compile / Target SDK:** API 36
* **JDK:** Java 17 / 21
* **Arsitektur:** Sederhana & Bersih (Cocok untuk Tugas Mahasiswa)

---

## 2. Fitur & Pembuktian Konsep Praktikum

| Konsep Praktikum | Implementasi pada Project | File Terkait |
|---|---|---|
| **Raw JSON Input** | Mock JSON lokal invoice fintech berstruktur disimpan internal (tanpa ditampilkan mentah ke UI) | `JsonDataProvider.kt`, `assets/invoice_data.json` |
| **Parsing Gson Otomatis** | `Gson().fromJson(rawJson, InvoiceResponse::class.java)` otomatis dieksekusi saat aplikasi dibuka | `MainActivity.kt` |
| **Kotlin Data Class DTO** | `@SerializedName` pada seluruh model bertingkat | `InvoiceResponse.kt` |
| **Direct Invoice Presentation** | Saat dibuka, APK langsung menampilkan UI invoice yang telah terisi lengkap | `MainActivity.kt`, `activity_main.xml` |
| **Nested Objects** | Objek `Merchant` dan `Customer` di dalam `InvoiceResponse` | `InvoiceResponse.kt` |
| **JSON Array** | List produk `items: List<InvoiceItem>` | `InvoiceResponse.kt` |
| **RecyclerView** | Menampilkan daftar produk dengan adapter dan ViewHolder | `InvoiceItemAdapter.kt`, `item_invoice_product.xml` |
| **Defensive Null Safety** | Penanganan `voucher_code` & `voucher_discount_percent` bernilai `null` tanpa crash | `InvoiceActivity.kt` |
| **Custom Date Formatter** | Format `2026-09-23 10:15:00` -> `Rabu, 23 September 2026 - Pukul 10:15 WIB` | `DateFormatter.kt` |
| **Format Rupiah** | Format mata uang `NumberFormat` dengan `Locale("id", "ID")` | `CurrencyHelper.kt` |
| **Kalkulasi Dinamis** | Total jenis item (`items.size`) & total kuantitas (`items.sumOf { it.qty }`) | `InvoiceActivity.kt` |
| **Salin Nomor Invoice** | Menyalin `INV-2026-FT9012` ke Android Clipboard | `InvoiceActivity.kt` |

---

## 3. Struktur Folder Project

```text
FintechBillingApp/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── assets/
│       │   │   └── invoice_data.json
│       │   ├── java/
│       │   │   └── com/industri/fintechbilling/
│       │   │       ├── MainActivity.kt
│       │   │       ├── InvoiceActivity.kt
│       │   │       ├── InvoiceResponse.kt
│       │   │       ├── InvoiceItemAdapter.kt
│       │   │       ├── DateFormatter.kt
│       │   │       ├── CurrencyHelper.kt
│       │   │       └── JsonDataProvider.kt
│       │   └── res/
│       │       ├── drawable/
│       │       │   ├── bg_badge_success.xml
│       │       │   ├── bg_badge_verified.xml
│       │       │   ├── bg_badge_unverified.xml
│       │       │   ├── bg_promo_card.xml
│       │       │   ├── bg_json_preview.xml
│       │       │   ├── ic_receipt.xml
│       │       │   ├── ic_copy.xml
│       │       │   ├── ic_check_circle.xml
│       │       │   ├── ic_store.xml
│       │       │   ├── ic_person.xml
│       │       │   ├── ic_shopping_bag.xml
│       │       │   ├── ic_payment.xml
│       │       │   ├── ic_discount.xml
│       │       │   ├── ic_calendar.xml
│       │       │   └── ic_arrow_back.xml
│       │       ├── layout/
│       │       │   ├── activity_main.xml
│       │       │   ├── activity_invoice.xml
│       │       │   └── item_invoice_product.xml
│       │       └── values/
│       │           ├── colors.xml
│       │           ├── strings.xml
│       │           └── themes.xml
│       └── test/
│           └── java/com/industri/fintechbilling/
│               └── InvoiceParsingTest.kt
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── local.properties
├── gradlew
└── gradlew.bat
```

---

## 4. Cara Membuka & Menjalankan di Android Studio

1. **Buka Android Studio**.
2. Pilih menu **File > Open...** (atau klik **Open** pada layar Welcome).
3. Arahkan dan pilih folder:
   ```text
   C:\Users\rawdi\.gemini\antigravity-ide\scratch\FintechBillingApp
   ```
   (Atau salin folder `FintechBillingApp` ke lokasi folder project Anda).
4. Tunggu hingga proses **Gradle Sync** selesai secara otomatis.
5. Jalankan aplikasi dengan memilih Emulator / Perangkat fisik, lalu klik tombol **Run 'app' (Shift + F10)**.

---

## 5. Hasil Build & Verifikasi Pengujian

Project telah diverifikasi secara menyeluruh menggunakan Gradle wrapper:
* **Unit Test (`testDebugUnitTest`):** `BUILD SUCCESSFUL` (100% lulus, memverifikasi parsing JSON, pemodelan DTO, formatting tanggal, dan kalkulasi).
* **Build APK (`assembleDebug`):** `BUILD SUCCESSFUL`, menghasilkan file installer:
  `app/build/outputs/apk/debug/app-debug.apk`.
