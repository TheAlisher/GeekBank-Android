package com.alish.geekbank.presentation.ui.fragments.payments.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentPaymentBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PaymentFragment : BaseFragment<PaymentViewModel,FragmentPaymentBinding>(R.layout.fragment_payment) {

    override val viewModel: PaymentViewModel by viewModels()
    override val binding: FragmentPaymentBinding by viewBinding(FragmentPaymentBinding::bind)
    var card: String? = null
    var moneyCard: Int? = null
    override fun setupListeners() {
        sendClick()
    }

    override fun setupRequests() {
        viewModel.stateCard.collectUIState {
            when(it){
                is UIState.Success -> {
                    it.data.forEach {
                        if (it?.cardNumber == card){
                            moneyCard = it?.money
                        }
                    }
                }
            }
        }
    }

    private fun sendClick() {
        binding.btnSend.setOnClickListener {
            val number = binding.editText.text.toString().trim()
            card = binding.editTextCard.text.toString().trim()
            val money = binding.etMoney.text.toString().trim()
            lifecycleScope.launch {
                viewModel.addHistory(money.toInt(),card,number,"payment", getTime())
            }
            lifecycleScope.launch {
                viewModel.updateAccount(moneyCard!! - money.toInt(),card.toString())
            }

        }
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val curentDate = sdf.format(Date())
        return curentDate

    }


}