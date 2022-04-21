package com.alish.geekbank.presentation.ui.fragments.profile


import com.alish.geekbank.domain.usecases.firestore.DownloadProfileImageUseCase
import com.alish.geekbank.domain.usecases.firestore.FetchUserDataUseCase
import com.alish.geekbank.domain.usecases.firestore.UploadProfileImageUseCase
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
class ProfileViewModel @Inject constructor(
    private val fetchUserDataUseCase: FetchUserDataUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val downloadProfileImageUseCase: DownloadProfileImageUseCase,
) : BaseViewModel() {
    private val _stateUser =
        MutableStateFlow<UIState<UsersModelUI?>>(UIState.Loading())
    val stateUser: StateFlow<UIState<UsersModelUI?>> = _stateUser.asStateFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        fetchUserDataUseCase().collectRequest(_stateUser) { it?.toUsersModelUI() }
    }

    suspend fun uploadProfileImage(file: ByteArray?, id: String) =
        uploadProfileImageUseCase.uploadProfileImage(file, id)

    suspend fun downloadProfileImage(id: String) =
        downloadProfileImageUseCase.downloadProfileImage(id)
}