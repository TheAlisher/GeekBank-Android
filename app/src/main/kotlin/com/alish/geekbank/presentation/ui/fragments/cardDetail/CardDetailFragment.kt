package com.alish.geekbank.presentation.ui.fragments.cardDetail

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailFragment : BaseFragment<CardDetailViewModel, FragmentCardDetailBinding>(R.layout.fragment_card_detail) {

    override val viewModel: CardDetailViewModel by viewModels()
    override val binding by viewBinding(FragmentCardDetailBinding::bind)

    override fun initialize() {
        BottomSheetBehavior.from(binding.sheetContainer)
    }
}