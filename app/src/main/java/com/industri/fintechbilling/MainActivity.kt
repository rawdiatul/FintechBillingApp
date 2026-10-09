package com.industri.fintechbilling

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    // Data JSON transaksi lokal sesuai spesifikasi Modul Pertemuan 11
    private val sampleInvoiceJson = """
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Parsing JSON menggunakan library Gson
        val gson = Gson()
        val invoice = gson.fromJson(sampleInvoiceJson, InvoiceResponse::class.java)

        // 2. Hubungkan data yang telah diparsing ke elemen UI (Views)
        displayInvoiceDetails(invoice)
    }

    private fun displayInvoiceDetails(invoice: InvoiceResponse) {
        // --- Referensi Elemen XML melalui findViewById ---
        // Informasi Transaksi
        val tvInvoiceNumber: TextView = findViewById(R.id.tvInvoiceNumber)
        val tvPaymentStatus: TextView = findViewById(R.id.tvPaymentStatus)
        val tvTransactionDate: TextView = findViewById(R.id.tvTransactionDate)
        val tvPaymentMethod: TextView = findViewById(R.id.tvPaymentMethod)

        // Informasi Merchant & Pelanggan
        val tvMerchantName: TextView = findViewById(R.id.tvMerchantName)
        val tvMerchantCity: TextView = findViewById(R.id.tvMerchantCity)
        val tvMerchantVerified: TextView = findViewById(R.id.tvMerchantVerified)
        val tvCustomerName: TextView = findViewById(R.id.tvCustomerName)
        val tvCustomerPhone: TextView = findViewById(R.id.tvCustomerPhone)

        // RecyclerView Daftar Barang
        val rvInvoiceItems: RecyclerView = findViewById(R.id.rvInvoiceItems)

        // Promo & Voucher (Tugas 1 - Defensive Nullable Handling)
        val tvPromoStatus: TextView = findViewById(R.id.tvPromoStatus)
        val layoutVoucherDetail: LinearLayout = findViewById(R.id.layoutVoucherDetail)
        val tvVoucherCode: TextView = findViewById(R.id.tvVoucherCode)
        val tvVoucherDiscountPercent: TextView = findViewById(R.id.tvVoucherDiscountPercent)

        // Ringkasan Pembayaran
        val tvSubtotal: TextView = findViewById(R.id.tvSubtotal)
        val tvDiscount: TextView = findViewById(R.id.tvDiscount)
        val tvTax: TextView = findViewById(R.id.tvTax)
        val tvServiceFee: TextView = findViewById(R.id.tvServiceFee)
        val tvTotalPaid: TextView = findViewById(R.id.tvTotalPaid)

        // --- 1. Tampilkan Data Transaksi ---
        tvInvoiceNumber.text = invoice.invoiceNumber
        tvPaymentStatus.text = invoice.paymentStatus
        tvPaymentMethod.text = invoice.paymentMethod

        // TUGAS 2: Custom Date Formatting menggunakan DateFormatter
        tvTransactionDate.text = DateFormatter.formatTransactionDate(invoice.transactionDate)

        // --- 2. Tampilkan Data Merchant & Customer ---
        tvMerchantName.text = invoice.merchant.merchantName
        tvMerchantCity.text = invoice.merchant.cityLocation
        if (invoice.merchant.isVerified) {
            tvMerchantVerified.text = "Terverifikasi"
            tvMerchantVerified.visibility = View.VISIBLE
        } else {
            tvMerchantVerified.text = "Belum Terverifikasi"
            tvMerchantVerified.visibility = View.VISIBLE
        }
        tvCustomerName.text = invoice.customer.fullName
        tvCustomerPhone.text = invoice.customer.phone

        // --- 3. Siapkan RecyclerView untuk Menampilkan Daftar Barang ---
        rvInvoiceItems.layoutManager = LinearLayoutManager(this)
        rvInvoiceItems.adapter = InvoiceItemAdapter(invoice.items)

        // --- 4. TUGAS 1: Defensive Nullable Handling untuk Voucher / Promo ---
        val summary = invoice.summary
        if (summary.voucherCode != null && summary.voucherDiscountPercent != null) {
            // Jika voucher tersedia, tampilkan status aktif dan baris rincian voucher
            tvPromoStatus.text = "Promo Digunakan"
            layoutVoucherDetail.visibility = View.VISIBLE
            tvVoucherCode.text = summary.voucherCode
            tvVoucherDiscountPercent.text = "${summary.voucherDiscountPercent}%"
        } else {
            // Jika voucher null atau tidak tersedia, tampilkan tulisan dan sembunyikan baris rincian voucher
            tvPromoStatus.text = "Tidak menggunakan promo"
            layoutVoucherDetail.visibility = View.GONE
        }

        // --- 5. Tampilkan Ringkasan Pembayaran dengan Format Rupiah ---
        tvSubtotal.text = CurrencyFormatter.formatRupiah(summary.subtotalAmount)
        tvDiscount.text = "-${CurrencyFormatter.formatRupiah(summary.discountAmount)}"
        tvTax.text = CurrencyFormatter.formatRupiah(summary.taxPpn11)
        tvServiceFee.text = CurrencyFormatter.formatRupiah(summary.serviceFee)
        tvTotalPaid.text = CurrencyFormatter.formatRupiah(summary.totalPaid)
    }
}
