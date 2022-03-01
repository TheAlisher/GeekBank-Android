package com.alish.geekbank.presentation.ui.fragments.qrCode

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.databinding.FragmentQrBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.fragments.cardDetail.CardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrFragment : BaseFragment<QrViewModel, FragmentQrBinding>(R.layout.fragment_qr) {

    override val viewModel: QrViewModel by viewModels()
    override val binding by viewBinding(FragmentQrBinding::bind)
}