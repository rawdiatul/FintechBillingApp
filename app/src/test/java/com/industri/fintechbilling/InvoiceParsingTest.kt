package com.industri.fintechbilling

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InvoiceParsingTest {

    private val sampleJson = """
        {
          "invoice_number": "INV-2026-FT9012",
          "transaction_date": "2026-09-23 10:15:00",
          "payment_status": "PAID_SETTLED",
          "payment_method": "QRIS_BCA",
          "merchant": {
            "merchant_id": "MCH-99201",
            "merchant_name": "Mega Elektrindo Ritel",
            "city_location": "Bandung",
            "is_verified": true
          },
          "customer": {
            "customer_id": "CUST-4412",
            "full_name": "Ahmad Fauzi",
            "phone": "081298765432"
          },
          "items": [
            {
              "item_id": "ITM-01",
              "item_name": "Kabel Type-C 65W Fast Charging",
              "qty": 2,
              "unit_price": 75000.0,
              "subtotal": 150000.0
            },
            {
              "item_id": "ITM-02",
              "item_name": "Adaptor GaN Charger 3-Port 100W",
              "qty": 1,
              "unit_price": 320000.0,
              "subtotal": 320000.0
            },
            {
              "item_id": "ITM-03",
              "item_name": "Mouse Wireless Silent Click Ergonomis",
              "qty": 1,
              "unit_price": 130000.0,
              "subtotal": 130000.0
            }
          ],
          "summary": {
            "subtotal_amount": 600000.0,
            "discount_amount": 50000.0,
            "tax_ppn_11": 60500.0,
            "service_fee": 2500.0,
            "total_paid": 613000.0,
            "voucher_code": null,
            "voucher_discount_percent": null
          }
        }
    """.trimIndent()

    @Test
    fun testJsonParsing_Success() {
        val gson = Gson()
        val invoice = gson.fromJson(sampleJson, InvoiceResponse::class.java)

        assertNotNull(invoice)
        assertEquals("INV-2026-FT9012", invoice.invoiceNumber)
        assertEquals("2026-09-23 10:15:00", invoice.transactionDate)
        assertEquals("PAID_SETTLED", invoice.paymentStatus)
        assertEquals("QRIS_BCA", invoice.paymentMethod)
        assertEquals("Mega Elektrindo Ritel", invoice.merchant.merchantName)
        assertEquals(true, invoice.merchant.isVerified)
        assertEquals("Ahmad Fauzi", invoice.customer.fullName)
        assertEquals("081298765432", invoice.customer.phone)
        assertEquals(3, invoice.items.size)
        assertEquals(613000.0, invoice.summary.totalPaid, 0.001)
    }

    @Test
    fun testTask1_DefensiveNullableHandling_NullVoucher() {
        val gson = Gson()
        val invoice = gson.fromJson(sampleJson, InvoiceResponse::class.java)

        assertNull(invoice.summary.voucherCode)
        assertNull(invoice.summary.voucherDiscountPercent)

        // Verifikasi logika fallback
        val isVoucherUsed = invoice.summary.voucherCode != null && invoice.summary.voucherDiscountPercent != null
        val promoText = if (isVoucherUsed) "Promo Digunakan" else "Tidak menggunakan promo"

        assertEquals(false, isVoucherUsed)
        assertEquals("Tidak menggunakan promo", promoText)
    }

    @Test
    fun testTask1_DefensiveNullableHandling_FilledVoucher() {
        val jsonWithVoucher = sampleJson
            .replace("\"voucher_code\": null", "\"voucher_code\": \"DISKONHEMAT\"")
            .replace("\"voucher_discount_percent\": null", "\"voucher_discount_percent\": 15")

        val gson = Gson()
        val invoice = gson.fromJson(jsonWithVoucher, InvoiceResponse::class.java)

        assertEquals("DISKONHEMAT", invoice.summary.voucherCode)
        assertEquals(15, invoice.summary.voucherDiscountPercent)

        val isVoucherUsed = invoice.summary.voucherCode != null && invoice.summary.voucherDiscountPercent != null
        val promoText = if (isVoucherUsed) "Promo Digunakan" else "Tidak menggunakan promo"

        assertEquals(true, isVoucherUsed)
        assertEquals("Promo Digunakan", promoText)
    }

    @Test
    fun testTask2_CustomDateFormatting_ExactMatch() {
        val inputDate = "2026-09-23 10:15:00"
        val expected = "Rabu, 23 September 2026 - Pukul 10:15 WIB"
        val result = DateFormatter.formatTransactionDate(inputDate)

        assertEquals(expected, result)
    }

    @Test
    fun testTask2_CustomDateFormatting_SafeFallback() {
        val invalidDate = "invalid-date-string"
        val result = DateFormatter.formatTransactionDate(invalidDate)

        // Tidak crash dan mengembalikan teks awal
        assertEquals(invalidDate, result)
    }

    @Test
    fun testCurrencyFormatting_Rupiah() {
        val totalPaid = 613000.0
        val formatted = CurrencyFormatter.formatRupiah(totalPaid)
        assertTrue(formatted.contains("613.000") && formatted.contains("Rp"))
    }
}
