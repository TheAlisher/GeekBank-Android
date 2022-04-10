package com.alish.geekbank.presentation.ui.fragments.profile

import com.alish.geekbank.domain.usecases.FetchUserDataUseCase
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
class ProfileViewModel @Inject constructor(private val fetchUserDataUseCase: FetchUserDataUseCase) :
    BaseViewModel() {
    private val _stateUser =
        MutableStateFlow<UIState<List<UsersModelUI?>>>(UIState.Loading())
    val stateUser: StateFlow<UIState<List<UsersModelUI?>>> = _stateUser.asStateFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        fetchUserDataUseCase().collectRequest(_stateUser) { it.map { data -> data?.toUsersModelUI() } }
    }
}