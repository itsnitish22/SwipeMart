package com.nitishsharma.swipemart.data.model

data class LoadingModel(
    val loading: LoadingState,
    val isListEmpty: Boolean,
)

enum class LoadingState {
    LOADING, ERROR, COMPLETED
}
