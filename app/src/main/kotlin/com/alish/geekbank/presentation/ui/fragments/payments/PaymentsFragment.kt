package com.alish.geekbank.presentation.ui.fragments.payments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.databinding.FragmentPaymentsBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.fragments.cardDetail.CardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsFragment : BaseFragment<PaymentsViewModel,FragmentPaymentsBinding>(R.layout.fragment_payments) {

    override val viewModel: PaymentsViewModel by viewModels()
    override val binding by viewBinding(FragmentPaymentsBinding::bind)

}