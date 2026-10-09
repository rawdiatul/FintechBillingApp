package com.industri.fintechbilling

import com.google.gson.annotations.SerializedName

/**
 * Model data barang transaksi pada faktur.
 */
data class InvoiceItem(
    @SerializedName("item_id")
    val itemId: String,

    @SerializedName("item_name")
    val itemName: String,

    @SerializedName("qty")
    val qty: Int,

    @SerializedName("unit_price")
    val unitPrice: Double,

    @SerializedName("subtotal")
    val subtotal: Double
)
