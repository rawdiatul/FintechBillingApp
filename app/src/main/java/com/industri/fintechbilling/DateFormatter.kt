package com.industri.fintechbilling

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Helper dan fungsi ekstensi untuk format tanggal transaksi (Tugas Mandiri 2).
 * Mengubah format "yyyy-MM-dd HH:mm:ss" menjadi "EEEE, dd MMMM yyyy - 'Pukul' HH:mm 'WIB'".
 */
object DateFormatter {

    /**
     * Memformat string tanggal dari format sumber menjadi format standar Indonesia (WIB).
     *
     * Contoh:
     * Input: "2026-09-23 10:15:00"
     * Output: "Rabu, 23 September 2026 - Pukul 10:15 WIB"
     *
     * Dilengkapi penanganan error yang aman (defensive handling) agar tidak crash
     * apabila format tanggal tidak sesuai.
     */
    fun formatTransactionDate(dateString: String?): String {
        if (dateString.isNullOrBlank()) {
            return "-"
        }

        return try {
            // Parser tanggal sumber
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("id", "ID"))
            inputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

            val parsedDate = inputFormat.parse(dateString) ?: return dateString

            // Formatter tanggal tujuan sesuai ketentuan modul
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy - 'Pukul' HH:mm 'WIB'", Locale("id", "ID"))
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

            outputFormat.format(parsedDate)
        } catch (e: Exception) {
            // Defensive handling: kembalikan string awal jika parsing gagal
            dateString
        }
    }
}

/**
 * Extension function Kotlin untuk mempermudah pemformatan tanggal langsung dari String.
 */
fun String?.toFormattedTransactionDate(): String {
    return DateFormatter.formatTransactionDate(this)
}
