package com.alish.geekbank.presentation.ui.fragments.pincode

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentPinCodeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.extensions.overrideOnBackPressed
import com.alish.geekbank.presentation.extensions.showToastShort
import com.alish.geekbank.presentation.extensions.vibrateDevice
import com.alish.geekbank.presentation.ui.fragments.biometricauthentication.AuthenticationError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PinCodeFragment :
    BaseFragment<BaseViewModel, FragmentPinCodeBinding>(R.layout.fragment_pin_code),
    View.OnClickListener {
    override val viewModel: BaseViewModel by viewModels()
    override val binding by viewBinding(FragmentPinCodeBinding::bind)
    private val args by navArgs<PinCodeFragmentArgs>()

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricManager: BiometricManager

    @Inject
    lateinit var preferences: PreferencesHelper


    private var numberList = ArrayList<String>()
    private var pasCode = ""
    private var pasCode1 = ""
    private var num1: String? = null
    private var num2: String? = null
    private var num3: String? = null
    private var num4: String? = null


    override fun initialize() {
        if (preferences.pinCode?.length == 0) {
            binding.title.text = getString(R.string.create_a_password)
            binding.description.isVisible = false
            binding.btnFingerprint.isVisible = false
        } else {
            binding.title.text = getString(R.string.enter_your_password)
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

    private fun setupViews() {
        if (args.fromSettings) {
            binding.backPinCode.isVisible = true
            binding.labelPinCode.isVisible = true
            binding.btnFingerprint.isVisible = false
            binding.description.isVisible = false
            binding.title.text = getString(R.string.enter_your_current_pin_code)
        }
    }


    override fun setupListeners() {
        binding.backPinCode.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.description.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordDialogFragment)
        }
        setupViews()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_01 -> {
                numberList.add("1")
                passNumber(numberList)
            }
            R.id.btn_02 -> {
                numberList.add("2")
                passNumber(numberList)
            }
            R.id.btn_03 -> {
                numberList.add("3")
                passNumber(numberList)
            }
            R.id.btn_04 -> {
                numberList.add("4")
                passNumber(numberList)
            }
            R.id.btn_05 -> {
                numberList.add("5")
                passNumber(numberList)
            }
            R.id.btn_06 -> {
                numberList.add("6")
                passNumber(numberList)
            }
            R.id.btn_07 -> {
                numberList.add("7")
                passNumber(numberList)
            }
            R.id.btn_08 -> {
                numberList.add("8")
                passNumber(numberList)
            }
            R.id.btn_09 -> {
                numberList.add("9")
                passNumber(numberList)
            }
            R.id.btn_0 -> {
                numberList.add("0")
                passNumber(numberList)
            }
            R.id.btn_clear -> onClear()

            R.id.btn_fingerprint -> {
                if (isBiometricFeatureAvailable()) {
                    biometricPrompt.authenticate(buildBiometricPrompt())
                } else {
                    showToastShort(getString(R.string.fingerprint_not_registered))
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
                    num1 = number_list[0]
                    binding.viewOval1.setBackgroundResource(R.drawable.view_green_oval)
                }
                2 -> {
                    num2 = number_list[1]
                    binding.viewOval2.setBackgroundResource(R.drawable.view_green_oval)
                }
                3 -> {
                    num3 = number_list[2]
                    binding.viewOval3.setBackgroundResource(R.drawable.view_green_oval)
                }
                4 -> {
                    num4 = number_list[3]
                    binding.viewOval4.setBackgroundResource(R.drawable.view_green_oval)
                    pasCode = num1 + num2 + num3 + num4
                    if (preferences.pinCode?.length == 0) {
                        creatingPinCode()
                    } else {
                        matchPassCode()
                    }
                }
            }
        }
    }


    private fun creatingPinCode() {
        binding.title.text = getString(R.string.confirm_the_password)
        preferences.pinCode = pasCode
        onClear()
    }

    private fun matchPassCode() {
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        if (args.fromSettings) {
            when {
                (preferences.pinCode == pasCode) -> {
                    binding.title.text = getString(R.string.create_a_password)
                    onClear()
                }

                (binding.title.text == getString(R.string.create_a_password) && pasCode.length == 4) -> {
                    binding.title.text = getString(R.string.confirm_the_password)
                    onClear()
                    pasCode1 = num1 + num2 + num3 + num4
                }

                (binding.title.text == getString(R.string.confirm_the_password) && pasCode == pasCode1) -> {
                    preferences.pinCode = pasCode
                    findNavController().navigate(R.id.homeFragment)
                   // preferences.putOnPinCode(true)
                }
                else -> {
                    showToastShort(getString(R.string.not_match))
                    onClear()
                    vibrateDevice(requireContext())
                    binding.viewOvals.startAnimation(animationBounce)
                }
            }


        } else {
            if (preferences.pinCode == pasCode) {
                findNavController().navigate(R.id.homeFragment)
                //preferences.putOnPinCode(true)
            } else {
                showToastShort(getString(R.string.does_not_match))
                onClear()
                vibrateDevice(requireContext())
                binding.viewOvals.startAnimation(animationBounce)
            }
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
        numberList.clear()
        pasCode = ""
    }

    private fun setupBiometricAuthentication() {
        biometricManager = BiometricManager.from(requireContext())
        val executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, biometricCallback)
    }

    private fun checkBiometricFeatureState() {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> setErrorNotice(getString(R.string.no_biometric))
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> setErrorNotice(getString(R.string.biometric_features_are_currently_unavailable))
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> setErrorNotice(getString(R.string.not_registered_any_biometric_credentials))
            BiometricManager.BIOMETRIC_SUCCESS -> {}
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {}
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {}
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {}
        }
    }

    private fun buildBiometricPrompt(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.verify_your_identity))
            .setDescription(getString(R.string.сonfirm_your_identity))
            .setNegativeButtonText(getString(R.string.cancel))
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
            //preferences.putOnPinCode(true)
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!args.fromSettings) {
            overrideOnBackPressed { activity?.finish() }
        } else {
            overrideOnBackPressed { findNavController().navigateUp() }
        }
    }
}