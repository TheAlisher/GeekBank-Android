package com.alish.geekbank.presentation.ui.fragments.cardDetail

import com.alish.geekbank.domain.usecases.foo.FetchFooUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.CardsUI
import com.alish.geekbank.presentation.models.toUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
) : BaseViewModel() {
    private val _cardDetailState =
        MutableStateFlow<UIState<List<CardsUI>>>(UIState.Loading())
    val cardDetailState: StateFlow<UIState<List<CardsUI>>> = _cardDetailState

}