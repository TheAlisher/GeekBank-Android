package com.alish.geekbank.presentation.ui.fragments.pincode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentInputPinCodeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.customviews.KeyPadListerner
import com.alish.geekbank.presentation.ui.fragments.biometricauthentication.AuthenticationError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InputPinCodeFragment : BaseFragment<CreatePinCodeViewModel, FragmentInputPinCodeBinding>(
    R.layout.fragment_create_pin_code
) {

    override lateinit var binding: FragmentInputPinCodeBinding
    override val viewModel: CreatePinCodeViewModel
        get() = TODO("Not yet implemented")

    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var biometricManager: BiometricManager

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInputPinCodeBinding.inflate(inflater)

        setupBiometricAuthentication()
        checkBiometricFeatureState()

        return binding.root
    }

    private fun setupBiometricAuthentication() {
        biometricManager = BiometricManager.from(requireContext())
        val executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, biometricCallback)
    }

    private fun checkBiometricFeatureState() {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> setErrorNotice("Sorry. It seems your device has no biometric hardware")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> setErrorNotice("Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> setErrorNotice("You have not registered any biometric credentials")
            BiometricManager.BIOMETRIC_SUCCESS -> {}
        }
    }

    private fun buildBiometricPrompt(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verify your identity")
            .setDescription("Confirm your identity so we can verify it's you")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(false) //Allows user to authenticate without performing an action, such as pressing a button, after their biometric credential is accepted.
            .build()
    }

    private fun isBiometricFeatureAvailable(): Boolean {
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
    }

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            findNavController().navigate(R.id.action_inputPinCodeFragment_to_homeFragment)
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)

            if (errorCode != AuthenticationError.AUTHENTICATION_DIALOG_DISMISSED.errorCode && errorCode != AuthenticationError.CANCELLED.errorCode) {
                setErrorNotice(errString.toString())
            }
        }
    }

    private fun setErrorNotice(errorMessage: String) {
        binding.tvErrorNotice.text = errorMessage
    }

    override fun initialize() {
        binding.key.setKeyPadListener(object : KeyPadListerner {

            override fun onKeyPadPressed(value: String?) {
                if (preferencesHelper.pinCode == value){
                    findNavController().navigate(R.id.action_inputPinCodeFragment_to_homeFragment)
                }else{
                    Toast.makeText(requireContext(), "wrong password", Toast.LENGTH_SHORT).show()
                    binding.key.doClearPin()
                }
            }

            override fun onKeyBackPressed() {
            }

            override fun onClear() {
                if (isBiometricFeatureAvailable()) {
                    biometricPrompt.authenticate(buildBiometricPrompt())
                }
            }
        })
    }
}