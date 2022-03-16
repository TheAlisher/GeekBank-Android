package com.alish.geekbank.presentation.ui.fragments.exchange

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentExchangeBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.exchange.ExchangeModelsUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.ExchangeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeFragment :
    BaseFragment<ExchangeViewModel, FragmentExchangeBinding>(R.layout.fragment_exchange) {

    override val viewModel: ExchangeViewModel by viewModels()
    override val binding by viewBinding(FragmentExchangeBinding::bind)
    private val adapter = ExchangeAdapter()

    override fun initialize() = with(binding) {
        rvDefCourse.layoutManager = LinearLayoutManager(context)
        rvDefCourse.adapter = adapter
    }

    override fun setupSubscribes() {
        viewModel.exchangeState.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    it.data.let { data ->
                        val list = ArrayList<ExchangeModelsUI>()
                        list.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["KGS"].toString(),
                                it.data.conversion_rates["KGS"].toString(),
                            )
                        )

                        list.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["USD"].toString(),
                                it.data.conversion_rates["USD"].toString(),
                            )
                        )

                        list.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["EUR"].toString(),
                                it.data.conversion_rates["EUR"].toString(),
                            )
                        )

                        list.add(
                            ExchangeModelsUI(
                                it.data.conversion_rates["RUS"].toString(),
                                it.data.conversion_rates["RUS"].toString(),
                            )
                        )

                        adapter.submitList(list)
                        Log.d("anime", "setupSubscribes: " + list)
                    }
                }
            }
        }
    }
}