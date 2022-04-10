package com.alish.geekbank.presentation.ui.fragments.freezeCard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alish.geekbank.databinding.FragmentFreezeDialogBinding
import com.alish.geekbank.presentation.ui.fragments.cardDetail.CardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.cacheGet

@AndroidEntryPoint
class FreezeDialogFragment : DialogFragment() {

    private var _binding: FragmentFreezeDialogBinding? = null
    private val binding get() = _binding!!
    private var cardNum: String? = null
    private val viewModel by viewModels<FreezeCardViewModel>()

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentFreezeDialogBinding.inflate(LayoutInflater.from(context))
        cardNum = FreezeDialogFragmentArgs.fromBundle(requireArguments()).cardNumber
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
            lifecycleScope.launch {
                viewModel.blockCard(cardNum.toString())
            }

            Toast.makeText(context,cardNum , Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
    }
}