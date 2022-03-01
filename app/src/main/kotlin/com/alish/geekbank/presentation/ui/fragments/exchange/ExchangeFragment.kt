package com.alish.geekbank.presentation.ui.fragments.exchange

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentExchangeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeFragment :
    BaseFragment<ExchangeViewModel, FragmentExchangeBinding>(R.layout.fragment_exchange) {

    override val viewModel: ExchangeViewModel by viewModels()
    override val binding by viewBinding(FragmentExchangeBinding::bind)
}