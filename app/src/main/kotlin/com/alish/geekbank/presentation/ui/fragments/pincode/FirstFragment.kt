package com.alish.geekbank.presentation.ui.fragments.pincode

import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentFirstBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.ui.fragments.biometricauthentication.AuthenticationError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment :
    BaseFragment<BaseViewModel, FragmentFirstBinding>(R.layout.fragment_first),
    View.OnClickListener {
    override val viewModel: BaseViewModel by viewModels()
    override val binding by viewBinding(FragmentFirstBinding::bind)

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricManager: BiometricManager


    @Inject
    lateinit var preferences: PreferencesHelper


    var number_list = ArrayList<String>()
    var pasCode = ""
    var num_1: String? = null
    var num_2: String? = null
    var num_3: String? = null
    var num_4: String? = null


    override fun initialize() {
        if (preferences.pinCode?.length == 0) {
            binding.title.text = Constants.CREATE
        } else {
            binding.title.text = Constants.ENTER
        }
        binding.btn01.setOnClickListener(this)
        binding.btn02.setOnClickListener(this)
        binding.btn03.setOnClickListener(this)
        binding.btn04.setOnClickListener(this)
        binding.btn05.setOnClickListener(this)
        binding.btn06.setOnClickListener(this)
        binding.btn07.setOnClickListener(this)
        binding.btn08.setOnClickListener(this)
        binding.btn09.setOnClickListener(this)
        binding.btn0.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
        binding.btnFingerprint.setOnClickListener(this)

        setupBiometricAuthentication()
        checkBiometricFeatureState()
    }

    override fun setupListeners() {
        binding.description.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordDialogFragment)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_01 -> {
                number_list.add("1")
                passNumber(number_list)
            }
            R.id.btn_02 -> {
                number_list.add("2")
                passNumber(number_list)
            }
            R.id.btn_03 -> {
                number_list.add("3")
                passNumber(number_list)
            }
            R.id.btn_04 -> {
                number_list.add("4")
                passNumber(number_list)
            }
            R.id.btn_05 -> {
                number_list.add("5")
                passNumber(number_list)
            }
            R.id.btn_06 -> {
                number_list.add("6")
                passNumber(number_list)
            }
            R.id.btn_07 -> {
                number_list.add("7")
                passNumber(number_list)
            }
            R.id.btn_08 -> {
                number_list.add("8")
                passNumber(number_list)
            }
            R.id.btn_09 -> {
                number_list.add("9")
                passNumber(number_list)
            }
            R.id.btn_0 -> {
                number_list.add("0")
                passNumber(number_list)
            }
            R.id.btn_clear -> onClear()

            R.id.btn_fingerprint -> {
                if (isBiometricFeatureAvailable()) {
                    biometricPrompt.authenticate(buildBiometricPrompt())
                }else{
                    Toast.makeText(requireContext(), "fingerprint not registered", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun passNumber(number_list: ArrayList<String>) {
        if (number_list.size == 0) {
            passwordClear()
        } else {
            when (number_list.size) {
                1 -> {
                    num_1 = number_list[0]
                    binding.viewOval1.setBackgroundResource(R.drawable.view_green_oval)
                }
                2 -> {
                    num_2 = number_list[1]
                    binding.viewOval2.setBackgroundResource(R.drawable.view_green_oval)
                }
                3 -> {
                    num_3 = number_list[2]
                    binding.viewOval3.setBackgroundResource(R.drawable.view_green_oval)
                }
                4 -> {
                    num_4 = number_list[3]
                    binding.viewOval4.setBackgroundResource(R.drawable.view_green_oval)
                    pasCode = num_1 + num_2 + num_3 + num_4

                    if (preferences.pinCode?.length == 0) {
                        binding.title.text = Constants.СONFIRM
                        preferences.pinCode = pasCode
                        onClear()
                    } else {
                        matchPassCode()
                    }
                }
            }
        }
    }

    private fun matchPassCode() {
        if (preferences.pinCode == pasCode) {
            findNavController().navigate(R.id.homeFragment)
        } else {
            Toast.makeText(requireContext(),
                "PassCode dosen`t match please retry again!!!",
                Toast.LENGTH_SHORT).show()
            onClear()

        }
    }

    private fun passwordClear() {
        binding.viewOval1.setBackgroundResource(R.drawable.view_grey_oval)
        binding.viewOval2.setBackgroundResource(R.drawable.view_grey_oval)
        binding.viewOval3.setBackgroundResource(R.drawable.view_grey_oval)
        binding.viewOval4.setBackgroundResource(R.drawable.view_grey_oval)
    }

    private fun onClear() {
        passwordClear()
        number_list.clear()
        pasCode = ""
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
            findNavController().navigate(R.id.homeFragment)
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)

            if (errorCode != AuthenticationError.AUTHENTICATION_DIALOG_DISMISSED.errorCode && errorCode != AuthenticationError.CANCELLED.errorCode) {
                setErrorNotice(errString.toString())
            }
        }
    }

    private fun setErrorNotice(errorMessage: String) {
        binding.descriptionFingerprint.text = errorMessage
    }
}