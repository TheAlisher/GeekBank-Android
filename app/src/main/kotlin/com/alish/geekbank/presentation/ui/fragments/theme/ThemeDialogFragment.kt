package com.alish.geekbank.presentation.ui.fragments.theme

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentThemeDialogBinding
import javax.inject.Inject

class ThemeDialogFragment : DialogFragment() {

    private var _binding: FragmentThemeDialogBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferencesHelp: PreferencesHelper

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

//        if (preferencesHelp.isNightModeOn()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//        btnThemeYes.setOnClickListener {
//            if (preferencesHelp.isNightModeOn()) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                preferencesHelp.editIsShown()
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//            dismiss()
//        }
//        btnThemeCancel.setOnClickListener {
//            dismiss()
//        }

    }

}