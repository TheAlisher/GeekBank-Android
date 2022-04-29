package com.alish.geekbank.presentation.ui.fragments.payments.payment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentPaymentBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.TransferModel
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardTransferAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class PaymentFragment : BaseFragment<PaymentViewModel,FragmentPaymentBinding>(R.layout.fragment_payment) {

    override val viewModel: PaymentViewModel by viewModels()
    override val binding: FragmentPaymentBinding by viewBinding(FragmentPaymentBinding::bind)
    var list = ArrayList<CardModelUI?>()
    private val adapter = CardTransferAdapter()
    private val card = TransferModel()
    override fun setupListeners() {
        checkPosition()
        sendClick()
    }


    private fun checkPosition() {
        binding.cardRecycler1.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset: Int = binding.cardRecycler1.computeHorizontalScrollOffset()
                var position: Float = offset.toFloat() / (binding.cardRecycler1.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                card.cardNumber = adapter.currentList[postInt].cardNumber.toString()
                card.money = adapter.currentList[postInt].money


            }
        })
    }

    override fun initialize() {
        binding.cardRecycler1.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.cardRecycler1.adapter = adapter

    }
    override fun setupRequests() {
        viewModel.stateCard.collectUIState {
            when(it){
                is UIState.Success -> {
                    adapter.submitList(it.data)
                }
            }
        }
    }

    private fun sendClick() {
        binding.btnSend.setOnClickListener {
            val number = binding.editText.text.toString().trim()
            val money = binding.etMoney.text.toString().trim()
            lifecycleScope.launch {
                viewModel.addHistory(money.toInt(),card.cardNumber.toString(),number,"service", getTime())
            }
            lifecycleScope.launch {
                viewModel.updateAccount(card.money!! - money.toInt(),card.cardNumber.toString())
            }
            findNavController().navigate(R.id.cardDetailFragment)
        }
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val curentDate = sdf.format(Date())
        return curentDate

    }


}