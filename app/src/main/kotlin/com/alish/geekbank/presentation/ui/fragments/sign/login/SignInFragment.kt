package com.alish.geekbank.presentation.ui.fragments.sign.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentSignInBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.mainNavController
import com.alish.geekbank.presentation.state.UIState
import com.google.api.LogDescriptor
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.internal.LogExceptionRunnable
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<SignInViewModel>()

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun setupListeners() {
        binding.loginBtn.setOnClickListener {
            if (signDetails()){
                signIn()
            }
        }
    }
    private fun signIn() = with(binding) {

        viewModel.signState.collectUIState {
            when (it) {
                is UIState.Error -> {
                    Log.e("tag", "error${it.error}")
                }
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    it.data.forEach { data ->
                        if (IDEt.text.toString().trim() == data?.id
                                && passwordEt.text.toString().trim() == data.password
                            ) {
                                preferencesHelper.putString(Constants.USER_ID,data.id)
                                preferencesHelper.putBoolean(Constants.IS_AUTHORIZED,true)
                                mainNavController().navigate(R.id.mainFlowFragment)

                        }
                    }
                }
            }
        }
    }
    private fun showToast(message: String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    private fun signDetails():Boolean{
        when {
            binding.IDEt.text.toString().trim().isEmpty() -> {
                showToast("Enter Login")
                return false
            }
            binding.passwordEt.text.toString().trim().isEmpty() -> {
                showToast("Enter Password")
                return false
            }
            binding.IDEt.text.toString().trim().length < 6 -> {
                showToast("Login must be bigger 7 simbols")
                return false
            }
            binding.passwordEt.text.toString().trim().length < 6 -> {
                showToast("Password must be bigger than 7 simbols")
                return false
            }
            else -> {
                return true
            }
        }
    }
}