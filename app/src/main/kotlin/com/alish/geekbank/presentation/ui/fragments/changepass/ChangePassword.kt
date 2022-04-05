package com.alish.geekbank.presentation.ui.fragments.changepass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentChangePasswordBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePassword : BaseFragment<ChangePassViewModel,FragmentChangePasswordBinding>(R.layout.fragment_change_password) {

    override val viewModel: ChangePassViewModel by viewModels()
    override val binding: FragmentChangePasswordBinding by viewBinding(FragmentChangePasswordBinding::bind)
    private var model: UsersModelUI? = UsersModelUI()

    override fun setupListeners() {
        changePass()
    }

    override fun setupRequests() {
        viewModel.stateUser.collectUIState {
            when(it){
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    model = it.data
                }
            }
        }
    }

    private fun changePass() {
        binding.btnSave.setOnClickListener {
            if (model?.password == binding.etOldPass.text.toString().trim() && binding.etNewPass.text.toString().trim() == binding.etConfirmPass.text.toString().trim() ){
                lifecycleScope.launch {
                    viewModel.updatePassword(binding.etConfirmPass.text.toString())
                }
                Toast.makeText(requireContext(),"Success",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.profileFragment)
            }
        }
    }

}