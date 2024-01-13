package com.nitishsharma.swipemart.main.home.epoxy

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.nitishsharma.swipemart.BR
import com.nitishsharma.swipemart.R

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class EpoxyProductItemModel : DataBindingEpoxyModel() {
    override fun getDefaultLayout(): Int {
        return R.layout.recycler_product
    }

    @EpoxyAttribute
    lateinit var productName: String

    @EpoxyAttribute
    lateinit var productType: String

    @EpoxyAttribute
    open var productPrice: Double = 0.00

    @EpoxyAttribute
    open var productImg: String? = null

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.productName, productName)
        binding.setVariable(BR.productType, productType)
        binding.setVariable(BR.productPrice, productPrice)
        binding.setVariable(BR.productImg, productImg)
    }
}

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class EpoxyNoMatchItemModel : DataBindingEpoxyModel() {
    override fun getDefaultLayout(): Int {
        return R.layout.no_match_error
    }
}