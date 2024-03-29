package com.alish.geekbank.presentation.ui.fragments.payments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentPaymentsBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.adapters.PaymentsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsFragment :
    BaseFragment<PaymentsViewModel, FragmentPaymentsBinding>(R.layout.fragment_payments) {

    override val viewModel: PaymentsViewModel by viewModels()
    override val binding by viewBinding(FragmentPaymentsBinding::bind)

    override fun initialize() {
        val adapter = PaymentsAdapter(viewModel.list!!, this::clickItem)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }


    private fun clickItem() {
        findNavController().navigate(R.id.paymentFragment)
    }

}