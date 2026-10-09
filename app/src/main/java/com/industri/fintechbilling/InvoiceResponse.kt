package com.industri.fintechbilling

import com.google.gson.annotations.SerializedName

/**
 * Model data utama respon faktur / transaksi.
 */
data class InvoiceResponse(
    @SerializedName("invoice_number")
    val invoiceNumber: String,

    @SerializedName("transaction_date")
    val transactionDate: String,

    @SerializedName("payment_status")
    val paymentStatus: String,

    @SerializedName("payment_method")
    val paymentMethod: String,

    @SerializedName("merchant")
    val merchant: Merchant,

    @SerializedName("customer")
    val customer: Customer,

    @SerializedName("items")
    val items: List<InvoiceItem>,

    @SerializedName("summary")
    val summary: BillingSummary
)
