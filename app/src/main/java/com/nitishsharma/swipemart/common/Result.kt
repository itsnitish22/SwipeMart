package com.nitishsharma.swipemart.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

val Result<*>.succeeded get() = this is Result.Success && data != null