package com.nitishsharma.swipemart.data.remote

import com.nitishsharma.swipemart.common.Result
import com.nitishsharma.swipemart.data.SwipeMartApiService
import com.nitishsharma.swipemart.data.model.Product
import okhttp3.MultipartBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SwipeMartRemoteRepoImpl : SwipeMartRemoteRepo, KoinComponent {
    private val swipeMartApiService: SwipeMartApiService by inject()

    override suspend fun getAllProducts(): Result<ArrayList<Product>> {
        return try {
            Result.Success(swipeMartApiService.getAllProducts())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addProduct(
        productType: String,
        productName: String,
        productPrice: String,
        taxRate: String,
        picture: MultipartBody.Part?
    ): Result<Unit> {
        return try {
            Result.Success(
                swipeMartApiService.addProduct(
                    productType,
                    productName,
                    productPrice,
                    taxRate,
                    picture
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}