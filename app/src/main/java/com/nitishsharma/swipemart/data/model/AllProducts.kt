package com.nitishsharma.swipemart.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("image")
    val productImage: String?,
    @SerializedName("price")
    val productPrice: Double?,
    @SerializedName("product_name")
    val productName: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("tax")
    val productTax: Float?
)
