package com.alish.geekbank.presentation.ui.fragments.admin.createcard

import com.alish.geekbank.domain.usecases.firestore.AddCardUseCase
import com.alish.geekbank.domain.usecases.firestore.CreateCardUseCase
import com.alish.geekbank.domain.usecases.firestore.FetchAllCardsUseCase
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
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CreateCardViewModel @Inject constructor(
    private val fetchUsersDataUseCase: FetchUsersDataUseCase,
    private val createCardUseCase: CreateCardUseCase,
    private val fetchAllCardsUseCase: FetchAllCardsUseCase,
    private val addCardUseCase: AddCardUseCase
): BaseViewModel() {

    private val _stateUsers: MutableStateFlow<UIState<List<UsersModelUI>>> = MutableStateFlow(UIState.Loading())
    val stateUsers: StateFlow<UIState<List<UsersModelUI>>> = _stateUsers

    private val _stateCards: MutableStateFlow<UIState<List<CardModelUI?>>> = MutableStateFlow(UIState.Loading())
    val stateCards: StateFlow<UIState<List<CardModelUI?>>> = _stateCards
    init {
        fetchUsers()
        fetchAllCards()
    }

    private fun fetchAllCards() {
        fetchAllCardsUseCase().collectRequest(_stateCards){it.map { data-> data?.toUI() }}
    }

    suspend fun createCard(cardNum: String,userId: String,money: Int,date: String,userFullName: String){
        val map = HashMap<String,Any>()
        val mapForAddCardToALl = HashMap<String,Any>()
        map["cardNumber"] = cardNum
        map["money"] = money
        map["date"] = date
        map["blocked"] = false
        map["fullName"] = userFullName
        mapForAddCardToALl["fullName"] = userFullName
        mapForAddCardToALl["blocked"] = false
        mapForAddCardToALl["cardNumber"] = cardNum
        createCardUseCase.createCard(map,userId,cardNum)
        addCardUseCase.addCards(mapForAddCardToALl,cardNum)
    }

    private fun fetchUsers() {
        fetchUsersDataUseCase().collectRequest(_stateUsers){it.map { data -> data.toUsersModelUI() }}
    }
}