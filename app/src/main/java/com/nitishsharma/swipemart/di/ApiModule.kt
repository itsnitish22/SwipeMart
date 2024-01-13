package com.nitishsharma.swipemart.di

import com.nitishsharma.swipemart.data.SwipeMartApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideApiService(retrofit: Retrofit): SwipeMartApiService {
        return retrofit.create(SwipeMartApiService::class.java)
    }
    single { provideApiService(get()) }
}