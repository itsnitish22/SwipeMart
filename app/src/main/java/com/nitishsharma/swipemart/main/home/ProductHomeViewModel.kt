package com.nitishsharma.swipemart.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitishsharma.swipemart.common.Event
import com.nitishsharma.swipemart.common.Result
import com.nitishsharma.swipemart.data.model.LoadingModel
import com.nitishsharma.swipemart.data.model.LoadingState
import com.nitishsharma.swipemart.data.model.Product
import com.nitishsharma.swipemart.data.remote.SwipeMartRemoteRepo
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.io.File

class ProductHomeViewModel : ViewModel(), KoinComponent {
    private val swipeMartRemoteRepo: SwipeMartRemoteRepo by inject()

    private val _loadingModel: MutableLiveData<LoadingModel> = MutableLiveData()
    val loadingModel: LiveData<LoadingModel> = _loadingModel

    private val _allProducts: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    val allProducts: LiveData<ArrayList<Product>> get() = _allProducts

    private val _productAdded: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val productAdded: LiveData<Event<Boolean>> get() = _productAdded

    private val _addProductLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val addProductLoading: LiveData<Boolean> get() = _addProductLoading

    private val _searchedProducts: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    val searchedProducts: LiveData<ArrayList<Product>> get() = _searchedProducts

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _loadingModel.postValue(LoadingModel(LoadingState.LOADING, isListEmpty()))
            val response = swipeMartRemoteRepo.getAllProducts()
            if (response is Result.Success) {
                response.data?.let {
                    _loadingModel.postValue(LoadingModel(LoadingState.COMPLETED, isListEmpty()))
                    _allProducts.postValue(it)
                }
            } else if (response is Result.Error) {
                _loadingModel.postValue(LoadingModel(LoadingState.ERROR, isListEmpty()))
                Timber.e("All Products Error: ${response.exception.message}")
            }
        }
    }

    fun filterList(searchKey: String) {
        val products = _allProducts.value
        products?.let { product ->
            if (product.size > 0) {
                val foundProducts = product.filter {
                    it.productName?.contains(
                        searchKey,
                        ignoreCase = true
                    ) == true
                } as ArrayList<Product>
                _searchedProducts.postValue(foundProducts)
            }
        }
    }

    fun addProduct(productType: String, productName: String, productPrice: String, file: File?) {
        var imageFile: MultipartBody.Part? = null
        file?.let {
            imageFile = MultipartBody.Part.createFormData(
                "fileUpload",
                it.name,
                it.asRequestBody("image/jpeg".toMediaType())
            )
        }
        viewModelScope.launch {
            _addProductLoading.postValue(true)
            val response = swipeMartRemoteRepo.addProduct(
                productType = productType,
                productName = productName,
                productPrice = productPrice,
                picture = imageFile
            )
            if (response is Result.Success) {
                _addProductLoading.postValue(false)
                _productAdded.postValue(Event(true))
            } else if (response is Result.Error) {
                _productAdded.postValue(Event(false))
                _addProductLoading.postValue(false)
                Timber.e(response.exception.message.toString())
            }
        }
    }

    private fun isListEmpty() = _allProducts.value?.isEmpty() ?: true
}