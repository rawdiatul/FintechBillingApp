package com.industri.fintechbilling

import com.google.gson.annotations.SerializedName

/**
 * Model data informasi pelanggan / customer.
 */
data class Customer(
    @SerializedName("customer_id")
    val customerId: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("phone")
    val phone: String
)
