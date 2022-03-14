package com.alish.geekbank.presentation.ui.fragments.qrCode

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.alish.geekbank.databinding.FragmentQrBinding
import com.alish.geekbank.presentation.base.BaseDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentQrBinding
    private val cardNumber: String = "4890494729240879"


    /*@SuppressLint("SetTextI18n")
    private fun setupListeners() {
        binding.ivOutput.setImageBitmap(
            generateQrCode(
                cardNumber = cardNumber
            )
        )
        binding.numberCard.text =
            "**** **** **** ****" + cardNumber.substring(cardNumber.length - 4)
    }

    private fun generateQrCode(cardNumber: String?): Bitmap? {
        val writer = MultiFormatWriter()
        var bitmap: Bitmap? = null

        try {
            val matrix = writer.encode(cardNumber, BarcodeFormat.QR_CODE, 550, 550)
            val encoder = BarcodeEncoder()
            bitmap = encoder.createBitmap(matrix)
        } catch (e: WriterException) {
        }
        return bitmap
    }*/
}