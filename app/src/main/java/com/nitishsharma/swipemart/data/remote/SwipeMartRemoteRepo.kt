package com.nitishsharma.swipemart.data.remote

import com.nitishsharma.swipemart.common.Result
import com.nitishsharma.swipemart.data.model.Product
import okhttp3.MultipartBody
import java.io.File

interface SwipeMartRemoteRepo {
    suspend fun getAllProducts(): Result<ArrayList<Product>>
    suspend fun addProduct(productType: String, productName: String, productPrice: String, taxRate: String = "18.00", picture: MultipartBody.Part? = null): Result<Unit>
}