package com.nitishsharma.swipemart.data

import com.nitishsharma.swipemart.data.model.Product
import okhttp3.MultipartBody
import org.koin.core.component.KoinComponent
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SwipeMartApiService : KoinComponent {
    @GET("api/public/get")
    suspend fun getAllProducts(): ArrayList<Product>

    @Multipart
    @POST("api/public/add")
    suspend fun addProduct(
        @Part("product_type") productType: String,
        @Part("product_name") productName: String,
        @Part("price") productPrice: String,
        @Part("tax") productTax: String,
        @Part filePart: MultipartBody.Part?
    ): Unit
}