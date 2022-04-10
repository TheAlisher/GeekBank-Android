package com.alish.geekbank.presentation.ui.fragments.editProfile

import com.alish.geekbank.domain.usecases.FetchUserDataUseCase
import com.alish.geekbank.domain.usecases.UpdateAccountUseCase
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
class EditProfileViewModel  @Inject constructor(private val updateAccountUseCase: UpdateAccountUseCase,
                                                private val fetchUserDataUseCase: FetchUserDataUseCase
): BaseViewModel() {

    private val _stateUser =
        MutableStateFlow<UIState<UsersModelUI?>>(UIState.Loading())
    val stateUser: StateFlow<UIState<UsersModelUI?>> = _stateUser.asStateFlow()


    init {
        fetchUser()
    }
    private fun fetchUser(){
        fetchUserDataUseCase().collectRequest(_stateUser){ it?.toUsersModelUI()}
    }
    suspend fun updateAccount(name: String,surname: String,number: String){
        val user = HashMap<String,Any>()
        user["name"] = name
        user["surname"] = surname
        user["number"] = number
        updateAccountUseCase.updateAccount(user)
    }
}