package com.alish.geekbank.presentation.ui.fragments.theme

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.alish.geekbank.databinding.FragmentFreezeDialogBinding
import com.alish.geekbank.databinding.FragmentThemeDialogBinding

class ThemeDialogFragment : DialogFragment() {

    private var _binding: FragmentThemeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentThemeDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setupButtonDialog()
        return builder
    }

    private fun setupButtonDialog() = with(binding) {
        btnThemeCancel.setOnClickListener {
            dismiss()
        }
        btnThemeYes.setOnClickListener {
            Toast.makeText(context, "You changed to dark theme!!", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

    }

}