package com.alish.geekbank.presentation.ui.fragments.changepass

import com.alish.geekbank.domain.models.firestore.UsersModel
import com.alish.geekbank.domain.usecases.firestore.FetchUserDataUseCase
import com.alish.geekbank.domain.usecases.firestore.UpdateAccountUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.models.toUsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class ChangePassViewModel @Inject constructor(
    private val changeAccountUseCase: UpdateAccountUseCase,
    private val fetchUserDataUseCase: FetchUserDataUseCase
) : BaseViewModel() {

    suspend fun updatePassword(pass: String){
        val map = HashMap<String,Any>()
        map["password"] = pass
        changeAccountUseCase.updateAccount(map)
    }
    private val _stateUser = MutableStateFlow<UIState<UsersModelUI?>>(UIState.Loading())
    val stateUser: StateFlow<UIState<UsersModelUI?>> = _stateUser
    init {
        fetchDataPass()
    }

    fun fetchDataPass(){
        fetchUserDataUseCase().collectRequest(_stateUser){it?.toUsersModelUI()}
    }
}