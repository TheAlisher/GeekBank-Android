package com.alish.geekbank.presentation.ui.fragments.exchange

import com.alish.geekbank.domain.usecases.exchenge.FetchExchangeUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.exchange.ExchangeModelUI
import com.alish.geekbank.presentation.models.exchange.toUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val fetchExchangeUseCase: FetchExchangeUseCase,
) : BaseViewModel() {

    private val _exchangeState =
        MutableStateFlow<UIState<ExchangeModelUI>>(UIState.Loading())
    var exchangeState: StateFlow<UIState<ExchangeModelUI>> = _exchangeState.asStateFlow()

    init {
        fetchExchange()
    }

    private fun fetchExchange() {
        fetchExchangeUseCase().collectRequest(_exchangeState) { it.toUI() }
    }
}