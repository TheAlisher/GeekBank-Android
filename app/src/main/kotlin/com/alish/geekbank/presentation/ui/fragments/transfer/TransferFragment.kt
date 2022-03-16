package com.alish.geekbank.presentation.ui.fragments.transfer

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentTransferBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardModel
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardTransferAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransferFragment :
    BaseFragment<TransferViewModel, FragmentTransferBinding>(R.layout.fragment_transfer) {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override val viewModel: TransferViewModel by viewModels()
    override val binding by viewBinding(FragmentTransferBinding::bind)
    private val adapterCard = CardTransferAdapter()

    override fun initialize() = with(binding) {
        cardRecycler1.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        cardRecycler1.adapter = adapterCard
        cardRecycler2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        cardRecycler2.adapter = adapterCard
    }

    override fun setupSubscribes() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    it.data.forEach { data ->
                        if (data?.id == preferencesHelper.getString("id")) {
                            val list = ArrayList<CardModel>()
                            list.add(
                                CardModel(
                                    data?.firstCard?.get("cardNumber").toString(),
                                    data?.firstCard?.get("name").toString(),
                                    data?.firstCard?.get("date").toString(),
                                    data?.firstCard?.get("money").toString(),
                                )
                            )

                            list.add(
                                CardModel(
                                    data?.secondCard?.get("cardNumber").toString(),
                                    data?.secondCard?.get("name").toString(),
                                    data?.secondCard?.get("date").toString(),
                                    data?.secondCard?.get("money").toString(),
                                )
                            )

                            adapterCard.submitList(list)
                            val myOnPageChangeCallback =
                                object : ViewPager2.OnPageChangeCallback() {
                                    override fun onPageSelected(position: Int) {
                                        when (position) {
                                            0 -> {
                                                binding.txtNumberAvailable.text =
                                                    data?.firstCard?.get("money").toString()
                                            }

                                            1 -> {
                                                binding.txtNumberAvailable.text =
                                                    data?.secondCard?.get("money").toString()
                                            }
                                        }
                                        binding.btnSetMoney.setOnClickListener {
                                            val money = binding.inputTxtTransfer.toString().trim()

                                        }
                                    }
                                }
                            binding.cardRecycler1.registerOnPageChangeCallback(
                                myOnPageChangeCallback)
                        }
                    }
                }
            }
        }
    }
}