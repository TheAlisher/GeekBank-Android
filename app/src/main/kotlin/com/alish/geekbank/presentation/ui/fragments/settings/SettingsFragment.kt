package com.alish.geekbank.presentation.ui.fragments.settings

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.databinding.FragmentSettingsBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.fragments.cardDetail.CardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsViewModel,FragmentSettingsBinding>(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModels()
    override val binding by viewBinding(FragmentSettingsBinding::bind)
}