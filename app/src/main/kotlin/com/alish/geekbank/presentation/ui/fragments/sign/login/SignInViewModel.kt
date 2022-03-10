package com.alish.geekbank.presentation.ui.fragments.sign.login

import androidx.lifecycle.viewModelScope
import com.alish.geekbank.domain.usecases.FetchDataUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.models.toUsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signUseCase: FetchDataUseCase,
) : BaseViewModel() {

    private val _signState = MutableStateFlow<UIState<List<UsersModelUI?>>>(UIState.Loading())
    val signState: StateFlow<UIState<List<UsersModelUI?>>> = _signState.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        signUseCase().collectRequest(_signState) {
            it.map { users ->
                users?.toUsersModelUI()
            }
        }
    }
}