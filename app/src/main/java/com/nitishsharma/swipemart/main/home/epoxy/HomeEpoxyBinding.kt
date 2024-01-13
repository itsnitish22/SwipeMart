package com.nitishsharma.swipemart.main.home.epoxy

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout
import com.nitishsharma.swipemart.R
import com.nitishsharma.swipemart.data.model.LoadingModel
import com.nitishsharma.swipemart.data.model.LoadingState
import timber.log.Timber

@SuppressLint("CheckResult")
@BindingAdapter("loadImageWithGlide")
fun loadImageWithGlide(imageView: AppCompatImageView, imageUrl: String?) {
    try {
        if (!imageUrl.isNullOrBlank()) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.jetha)
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(R.drawable.jetha)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
        }
    } catch (e: Exception) {
        Timber.e(e, "Error loading image: ${e.message}")
    }
}

@BindingAdapter("updateErrorState")
fun updateErrorState(view: ConstraintLayout, loadingObj: LoadingModel?) {
    if (loadingObj?.loading == LoadingState.ERROR && loadingObj.isListEmpty) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setErrorImage")
fun setErrorImage(view: AppCompatImageView, loadingObj: LoadingModel?) {
    loadingObj?.let {
        val drawable = ContextCompat.getDrawable(view.context, R.drawable.error_image)
        Glide.with(view).load(drawable).into(view)
    }
}

@BindingAdapter("setErrorText")
fun setErrorText(view: TextView, loadingObj: LoadingModel?) {
    loadingObj?.let {
        view.text = "Something went wrong! Try refreshing."
    }
}

@BindingAdapter("updateLoadingErrorRootViewVisibility")
fun updateLoadingErrorRootViewVisibility(view: View, loadingObj: LoadingModel?) {
    loadingObj?.let {
        if (it.loading != LoadingState.COMPLETED && it.isListEmpty) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("addProductLoading")
fun addProductLoading(view: View, loading: Boolean) {
    view.visibility = if (loading) View.VISIBLE else View.GONE
}

@BindingAdapter("updateShimmerLoading")
fun updateShimmerLoading(view: ShimmerFrameLayout, loadingObj: LoadingModel?) {
    if (loadingObj?.loading == LoadingState.LOADING && loadingObj.isListEmpty) {
        view.visibility = View.VISIBLE
        view.startShimmer()
    } else {
        view.visibility = View.GONE
        view.stopShimmer()
    }
}