package com.nitishsharma.swipemart.common

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import kotlin.math.abs

fun Fragment.isImageWithAspectRatioOneToOne(uri: Uri): Boolean {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    try {
        BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(uri), null, options)
        val aspectRatio = options.outWidth.toFloat() / options.outHeight.toFloat()
        val epsilon = 0.01
        return abs(aspectRatio - 1.0) < epsilon
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return false
}

fun View.snackBarWithString(str: String) {
    Snackbar.make(this, str, Snackbar.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(
        this, message,
        if (message.length <= 25) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()
}

fun Fragment.toast(msg: String) {
    requireContext().toast(msg)
}