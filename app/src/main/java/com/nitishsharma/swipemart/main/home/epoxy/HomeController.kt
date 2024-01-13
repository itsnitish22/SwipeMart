package com.nitishsharma.swipemart.main.home.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import com.nitishsharma.swipemart.data.model.Product

class HomeController() : Typed2EpoxyController<ArrayList<Product>?, Boolean>() {
    override fun buildModels(
        allProducts: ArrayList<Product>?,
        showNoMatch: Boolean
    ) {
        if (!showNoMatch) {
            allProducts?.forEachIndexed { index, product ->
                epoxyProductItem {
                    id("product$index")
                    productName(product.productName?.removeSurrounding("''"))
                    productImg(product.productImage)
                    productType(product.productType?.removeSurrounding("''"))
                    productPrice(product.productPrice ?: 100.90)
                }
            }
        } else {
            epoxyNoMatchItem {
                id("no-match")
            }
        }
    }
}