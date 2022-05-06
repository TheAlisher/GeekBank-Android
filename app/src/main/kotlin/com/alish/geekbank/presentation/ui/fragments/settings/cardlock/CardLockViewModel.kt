package com.alish.geekbank.presentation.ui.fragments.settings.cardlock

import com.alish.geekbank.domain.usecases.firestore.FetchCardDataUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.toUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CardLockViewModel @Inject constructor(
    private val fetchCardDataUseCase: FetchCardDataUseCase
) : BaseViewModel() {

    private val _stateCard =
        MutableStateFlow<UIState<List<CardModelUI?>>>(UIState.Loading())
    val stateCard: StateFlow<UIState<List<CardModelUI?>>> = _stateCard.asStateFlow()

    init {
        fetchCard()
    }

    private fun fetchCard() {
        fetchCardDataUseCase().collectRequest(_stateCard) { it.map { data -> data?.toUI() } }
    }
}