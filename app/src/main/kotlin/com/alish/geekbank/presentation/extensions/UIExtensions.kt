package com.alish.geekbank.presentation.extensions

import android.content.pm.PackageManager
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alish.geekbank.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


// region Fragment

/**
 * Fragment Extensions
 */

fun ImageView.setImage(uri: String) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .error(R.drawable.placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
fun EditText.check():Boolean{
    return this.text.toString().trim() != ""
}
fun EditText.textGet(): String {
    return this.text.toString().trim()
}

fun String.checkLength(): String{
    return if (this.length < 2){
        "0$this"
    }else{
        this
    }
}

fun Fragment.hasPermissionCheckAndRequest(
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    permission: Array<String>,
): Boolean {
    for (per in permission) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                per
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(permission)
            return false
        }
    }
    return true
}

fun Fragment.showToastShort(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastShort(@StringRes textFromRes: Int) {
    Toast.makeText(context, textFromRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastLong(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun Fragment.showToastLong(@StringRes textFromRes: Int) {
    Toast.makeText(context, textFromRes, Toast.LENGTH_LONG).show()
}

// endregion


// region Views

/**
 * View Extensions
 */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

// endregion