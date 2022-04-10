package com.alish.geekbank.presentation.ui.fragments.transfer

import com.alish.geekbank.domain.usecases.firestore.AddHistoryUseCases
import com.alish.geekbank.domain.usecases.firestore.FetchCardDataUseCase
import com.alish.geekbank.domain.usecases.firestore.UpdateCardsUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.toUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val fetchCardDataUseCase: FetchCardDataUseCase,
    private val updateCardsUseCase: UpdateCardsUseCase,
    private val addHistoryUseCases: AddHistoryUseCases
):BaseViewModel() {
    private val _stateCard =
        MutableStateFlow<UIState<List<CardModelUI?>>>(UIState.Loading())
    val stateCard: StateFlow<UIState<List<CardModelUI?>>> = _stateCard.asStateFlow()


    init {
        fetchCardData()
    }
    suspend fun updateAccount(money: Int,cardNumber: String){
        val user =HashMap<String,Any>()
        user["money"] = money
        updateCardsUseCase.updateAccount(user,cardNumber)
    }

    suspend fun addHistory(money: Int,fromCard: String?,toCard: String?,condition: String?,dateTime: String?){
        val map = HashMap<String,Any>()
        map["fromCard"] = fromCard.toString()
        map["toCard"] = toCard.toString()
        map["dateTime"] = dateTime.toString()
        map["condition"] = condition.toString()
        map["money"] = money
        addHistoryUseCases.addHistory(map)
    }


    private fun fetchCardData() {
        fetchCardDataUseCase().collectRequest(_stateCard) { it.map { data -> data?.toUI() } }
    }
}