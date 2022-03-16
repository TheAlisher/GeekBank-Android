package com.alish.geekbank.presentation.ui.fragments.freezeCard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.alish.geekbank.databinding.FragmentFreezeDialogBinding


class FreezeDialogFragment : DialogFragment() {

    private var _binding: FragmentFreezeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentFreezeDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setupButtonDialog()
        return builder
    }

    private fun setupButtonDialog() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnFreezeCard.setOnClickListener {
            Toast.makeText(context, "You have frozen the card!!", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

    }


}