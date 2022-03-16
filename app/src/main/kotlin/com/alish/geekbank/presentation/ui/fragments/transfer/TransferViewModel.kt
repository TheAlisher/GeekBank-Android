package com.alish.geekbank.presentation.ui.fragments.transfer

import com.alish.geekbank.domain.usecases.FetchDataUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.models.toUsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val fetchDataUseCase: FetchDataUseCase,
):BaseViewModel() {
    private val _stateCard =
        MutableStateFlow<UIState<List<UsersModelUI?>>>(UIState.Loading())
    val stateCard: StateFlow<UIState<List<UsersModelUI?>>> = _stateCard.asStateFlow()

    init {
        fetchCardData()
    }

    private fun fetchCardData() {
        fetchDataUseCase().collectRequest(_stateCard) { it.map { data -> data?.toUsersModelUI() } }
    }
}