package com.alish.geekbank.presentation.ui.fragments.admin.createuser

import com.alish.geekbank.domain.usecases.firestore.AddNewUserUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val createAcc: AddNewUserUseCase
) : BaseViewModel() {

    suspend fun createUser(name: String,
    lastName: String
    ,phone: String
    ,pass: String
    ,login: String
    ,condition: String){
        val map = HashMap<String,Any>()
        map["id"] = login
        map["password"] = pass
        map["name"] = name
        map["surname"] = lastName
        map["number"] = phone
        map["condition"] = condition
        createAcc.updateAccount(map,login)
    }
}