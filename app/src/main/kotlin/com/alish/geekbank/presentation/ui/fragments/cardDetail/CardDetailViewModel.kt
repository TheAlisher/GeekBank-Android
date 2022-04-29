package com.alish.geekbank.presentation.ui.fragments.cardDetail

import com.alish.geekbank.domain.usecases.firestore.FetchCardDataUseCase
import com.alish.geekbank.domain.usecases.firestore.FetchHistoryUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.HistoryModelUI
import com.alish.geekbank.presentation.models.toUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    private val fetchCardDataUseCase: FetchCardDataUseCase,
    private val fetchHistoryUseCase: FetchHistoryUseCase

) : BaseViewModel() {

    private val _stateCard =
        MutableStateFlow<UIState<List<CardModelUI?>>>(UIState.Loading())
    val stateCard: StateFlow<UIState<List<CardModelUI?>>> = _stateCard.asStateFlow()

    private val _stateHistory =
        MutableStateFlow<UIState<List<HistoryModelUI?>>>(UIState.Loading())
    val stateHistory: StateFlow<UIState<List<HistoryModelUI?>>> = _stateHistory.asStateFlow()

    init {
        fetchUserData()
        fetchHistory()
    }

    private fun fetchUserData(){
        fetchCardDataUseCase().collectRequest(_stateCard){it.map { data -> data?.toUI() }}
    }

    private fun fetchHistory(){
        fetchHistoryUseCase().collectRequest(_stateHistory){it.map { data -> data.toUI() }}
    }
    var cardNum: String = ""
}