package com.industri.fintechbilling

import java.text.NumberFormat
import java.util.Locale

/**
 * Helper pemformatan nilai mata uang Rupiah Indonesia.
 * Contoh: 613000.0 -> "Rp613.000"
 */
object CurrencyFormatter {
    fun formatRupiah(amount: Double): String {
        val localeId = Locale("id", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeId)
        formatter.maximumFractionDigits = 0
        return formatter.format(amount)
    }
}
