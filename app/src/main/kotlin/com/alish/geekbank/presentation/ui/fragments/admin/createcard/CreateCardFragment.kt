package com.alish.geekbank.presentation.ui.fragments.admin.createcard

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCreateCardBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.checkLength
import com.alish.geekbank.presentation.extensions.showToastShort
import com.alish.geekbank.presentation.extensions.textGet
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCardFragment :
    BaseFragment<CreateCardViewModel, FragmentCreateCardBinding>(R.layout.fragment_create_card) {
    override val viewModel: CreateCardViewModel by viewModels()
    override val binding: FragmentCreateCardBinding by viewBinding(FragmentCreateCardBinding::bind)
    private val userList = ArrayList<UsersModelUI>()
    private val cardsList = ArrayList<CardModelUI?>()

    override fun setupListeners() {
        clickCreateCard()
    }

    private fun clickCreateCard() = with(binding) {
        btnSave.setOnClickListener {
            var userId: String? = null
            var userFullName: String? = null
            var condition = true
            for (i in userList) {
                if (createDetails(inputUserId.textGet(), i.id!!)) {
                    userId = i.id
                    userFullName = i.name + " " + i.surname
                }
            }
            for (i in cardsList) {
                if (inputCard.textGet() == i?.cardNumber) {
                    this@CreateCardFragment.showToastShort("Такая карта есть")
                    condition = false
                    break
                } else {
                    condition = true
                }
            }

            if (userId != null && userFullName != null && condition) {
                val money = (10000..100000).random()
                val getRandomDate =
                    (1..12).random().toString().checkLength() + "/" + (24..27).random().toString()
                lifecycleScope.launch {
                    viewModel.createCard(
                        inputCard.textGet(),
                        userId,
                        money,
                        getRandomDate,
                        userFullName
                    )
                }
                findNavController().navigate(R.id.adminFragment)
                this@CreateCardFragment.showToastShort("Uspeshno")
            }
            if (userId == null) {
                this@CreateCardFragment.showToastShort("User ne nayden")
            }


        }
    }

    override fun setupRequests() {
        viewModel.stateUsers.collectUIState {
            when (it) {
                is UIState.Error -> {
                }
                is UIState.Loading -> {

                }
                is UIState.Success -> {
                    userList.addAll(it.data)

                }
            }
        }
        viewModel.stateCards.collectUIState {
            when (it) {
                is UIState.Error -> {
                }
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    cardsList.addAll(it.data)
                }
            }
        }
    }

    fun createDetails(id: String, idFromFirestore: String): Boolean {
        return id == idFromFirestore
    }


}