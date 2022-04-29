package com.alish.geekbank.presentation.ui.fragments.home.cards


import com.alish.geekbank.domain.usecases.firestore.FetchCardDataUseCase
import com.alish.geekbank.domain.usecases.firestore.FetchUsersDataUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.models.toUI
import com.alish.geekbank.presentation.models.toUsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class CardViewModel @Inject constructor(private val fetchCardDataUseCase: FetchCardDataUseCase): BaseViewModel() {

    private val _stateUser =
        MutableStateFlow<UIState<List<CardModelUI?>>>(UIState.Loading())
    val stateUser: StateFlow<UIState<List<CardModelUI?>>> = _stateUser.asStateFlow()
    init {
        fetchUserData()
    }
    private fun fetchUserData(){
        fetchCardDataUseCase().collectRequest(_stateUser){it.map { data -> data?.toUI() }}
    }
}