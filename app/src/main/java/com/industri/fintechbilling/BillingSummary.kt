package com.industri.fintechbilling

import com.google.gson.annotations.SerializedName

/**
 * Model data ringkasan pembayaran tagihan.
 * Properti voucher nullable untuk mendukung implementasi defensive nullable handling (Tugas 1).
 */
data class BillingSummary(
    @SerializedName("subtotal_amount")
    val subtotalAmount: Double,

    @SerializedName("discount_amount")
    val discountAmount: Double,

    @SerializedName("tax_ppn_11")
    val taxPpn11: Double,

    @SerializedName("service_fee")
    val serviceFee: Double,

    @SerializedName("total_paid")
    val totalPaid: Double,

    @SerializedName("voucher_code")
    val voucherCode: String? = null,

    @SerializedName("voucher_discount_percent")
    val voucherDiscountPercent: Int? = null
)
