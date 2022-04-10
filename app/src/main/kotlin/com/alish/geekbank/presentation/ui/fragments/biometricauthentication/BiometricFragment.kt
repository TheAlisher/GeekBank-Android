package com.alish.geekbank.presentation.ui.fragments.biometricauthentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentBiometricBinding
import com.alish.geekbank.databinding.FragmentCreatePinCodeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.fragments.pincode.CreatePinCodeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BiometricFragment() : BaseFragment<CreatePinCodeViewModel, FragmentBiometricBinding>(
    R.layout.fragment_biometric
) {
    override lateinit var binding: FragmentBiometricBinding
    override val viewModel: CreatePinCodeViewModel
        get() {
            TODO()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBiometricBinding.inflate(inflater)
        return binding.root
    }

}