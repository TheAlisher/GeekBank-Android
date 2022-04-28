package com.alish.geekbank.presentation.ui.fragments.admin.createuser

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.CreateUserBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.check
import com.alish.geekbank.presentation.extensions.textGet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateUserFragment :
    BaseFragment<CreateUserViewModel, CreateUserBinding>(R.layout.create_user) {

    override val viewModel: CreateUserViewModel by viewModels()
    override val binding: CreateUserBinding by viewBinding(CreateUserBinding::bind)


    override fun setupListeners() {
        createUserClick()
    }

    private fun createUserClick() = with(binding) {
        btnSave.setOnClickListener {
            if (inputFirstName.check() && inputLastName.check() && inputLogin.check() && inputNumber.check() && inputPass.check()) {
                lifecycleScope.launch {
                    viewModel.createUser(
                        inputFirstName.textGet(),
                        inputLastName.textGet(),
                        inputNumber.textGet(),
                        inputPass.textGet(),
                        inputLogin.textGet(),
                        condition.selectedItem.toString()
                    )
                }

            }
            findNavController().navigate(R.id.profileFragment)
        }
    }


}