package com.alish.geekbank.presentation.ui.fragments.transfer

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentTransferBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardTransferAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransferFragment :
    BaseFragment<TransferViewModel, FragmentTransferBinding>(R.layout.fragment_transfer) {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private var moneyCurrent: String? = null

    override val viewModel: TransferViewModel by viewModels()
    override val binding by viewBinding(FragmentTransferBinding::bind)
    private val adapterCard = CardTransferAdapter()

    override fun initialize() = with(binding) {
        cardRecycler1.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        cardRecycler1.adapter = adapterCard
        cardRecycler2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        cardRecycler2.adapter = adapterCard
    }

    override fun setupListeners() {
        sendListeners()

    }

    private fun sendListeners() = with(binding) {
        btnSendd.setOnClickListener {
            var money = inputTxtTransfer.text.toString()
            var changedMoney: Int = moneyCurrent!!.toInt() - money.toInt()
            lifecycleScope.launch {
                viewModel.updateAccount(
                    changedMoney,"1111222233334444").toString()

            }
            Toast.makeText(requireContext(), "$changedMoney", Toast.LENGTH_SHORT).show()

        }
    }

    override fun setupSubscribes() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    val list = ArrayList<CardModelUI>()
                    it.data.forEach { data ->
                        if (data?.id == preferencesHelper.getString(Constants.USER_ID)) {
                            list.add(data!!)
                            adapterCard.submitList(list)
                            if (moneyCurrent == null)
                                moneyCurrent = data.money.toString()

                        }
                    }
                }
            }
        }
    }
}