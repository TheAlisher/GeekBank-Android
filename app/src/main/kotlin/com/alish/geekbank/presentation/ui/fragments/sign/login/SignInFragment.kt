package com.alish.geekbank.presentation.ui.fragments.sign.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentSignInBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.mainNavController
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<SignInViewModel>()

    override fun setupSubscribes() = with(binding) {
        viewModel.signState.collectUIState {
            when (it) {
                is UIState.Error -> {
                    Log.e("tag", "error${it.error}")
                }
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    btnIn.setOnClickListener { _ ->

                        it.data.forEach { data ->
                            Toast.makeText(requireContext(), "Click bar", Toast.LENGTH_SHORT).show()

                            if (editId.text.toString().trim() == data?.id
                                && editPassword.text.toString().trim() == data.password
                            ) {
                                Log.e("confirm", "confirm success")
                                mainNavController().navigate(
                                    R.id.action_signFlowFragment_to_mainFlowFragment
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}