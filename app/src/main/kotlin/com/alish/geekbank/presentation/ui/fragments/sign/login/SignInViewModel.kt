package com.alish.geekbank.presentation.ui.fragments.sign.login

import com.alish.geekbank.data.repositories.FireStoreRepositoryImpl
import com.alish.geekbank.domain.usecases.firestore.FetchUsersDataUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.UsersModelUI
import com.alish.geekbank.presentation.models.toUsersModelUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signUseCaseUser: FetchUsersDataUseCase,
    private val repositoryImpl: FireStoreRepositoryImpl
) : BaseViewModel() {

    private val _signState = MutableStateFlow<UIState<List<UsersModelUI?>>>(UIState.Loading())
    val signState: StateFlow<UIState<List<UsersModelUI?>>> = _signState.asStateFlow()



    init {
        getData()

    }




    private fun getData() {
        signUseCaseUser().collectRequest(_signState) {
            it.map { users ->
                users.toUsersModelUI()
            }
        }
    }
}