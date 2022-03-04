package com.alish.geekbank.presentation.ui.fragments.sign.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SignInViewModel : ViewModel() {

    private val repository = LoginRepository()


    fun getData():CollectionReference?{
        return repository.checkData()
    }
}