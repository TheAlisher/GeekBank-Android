package com.alish.geekbank.presentation.extensions

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream

fun Fragment.compressJPEG(
    photo: Uri?,
    baos: ByteArrayOutputStream,
    quality: Int,
): ByteArray {
    val mBitmap = when {
        Build.VERSION.SDK_INT >= 29 && photo != null -> {
            val source = ImageDecoder.createSource(requireContext().contentResolver,
                photo)
            val bitmap = ImageDecoder.decodeBitmap(source)
            bitmap
        }
        Build.VERSION.SDK_INT <= 29 && photo != null -> {
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,
                photo)
            bitmap
        }
        else -> {
            null
        }
    }
    mBitmap?.compress(Bitmap.CompressFormat.JPEG, quality, baos)
    return baos.toByteArray()
}