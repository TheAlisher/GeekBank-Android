package com.alish.geekbank.presentation.ui.fragments.pincode

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.alish.geekbank.databinding.FragmentForgotPasswordBinding


class ForgotPasswordDialogFragment : DialogFragment() {
    private var _binding: FragmentForgotPasswordBinding? = null

    private val binding get() = _binding!!


    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentForgotPasswordBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return builder
    }


}