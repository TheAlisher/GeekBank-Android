package com.alish.geekbank.presentation.ui.fragments.pincode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCreatePinCodeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.customviews.KeyPadListerner
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePinCodeFragment : BaseFragment<CreatePinCodeViewModel, FragmentCreatePinCodeBinding>(
    R.layout.fragment_create_pin_code
) {
    @Inject
    lateinit var preferences: PreferencesHelper
    override lateinit var binding: FragmentCreatePinCodeBinding
    override val viewModel: CreatePinCodeViewModel
        get() = TODO("Not yet implemented")
    private var isConfirm = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreatePinCodeBinding.inflate(inflater)
        return binding.root
    }

    override fun setupListeners() {
        binding.key.setKeyPadListener(object : KeyPadListerner {
            override fun onKeyPadPressed(value: String?) {
                if (value?.length == 4) {
                    binding.title.text = Constants.СONFIRM
                    binding.key.clearAll()
                }
                if (isConfirm) {
                    if (value == preferences.pinCode) {
                        preferences.onSavePinCode()
                        findNavController().navigate(R.id.action_pinCodeFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "wrong password", Toast.LENGTH_SHORT)
                            .show()
                        binding.key.clearAll()
                    }
                } else {
                    Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
                    preferences.pinCode = value
                    isConfirm = true
                }
            }
            override fun onKeyBackPressed() {
            }
            override fun onClear() {
            }
        })
    }
}