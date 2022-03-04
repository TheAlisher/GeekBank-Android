package com.alish.geekbank.presentation.ui.fragments.transfer

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentTransferBinding
import com.alish.geekbank.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment :
    BaseFragment<TransferViewModel, FragmentTransferBinding>(R.layout.fragment_transfer) {

    override val viewModel: TransferViewModel by viewModels()
    override val binding by viewBinding(FragmentTransferBinding::bind)

    override fun setupListeners() {
        binding.arrowLeft.setOnClickListener {
            findNavController().navigate(TransferFragmentDirections.actionTransferFragmentToCardDetailFragment())
        }
    }

}